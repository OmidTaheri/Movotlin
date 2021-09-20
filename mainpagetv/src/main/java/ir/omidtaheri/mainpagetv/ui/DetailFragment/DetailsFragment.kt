package ir.omidtaheri.mainpagetv.ui.DetailFragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.*
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ir.omidtaheri.androidbase.BaseDetailTvFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpagetv.BuildConfig
import ir.omidtaheri.mainpagetv.R
import ir.omidtaheri.mainpagetv.di.components.DaggerDetailComponent
import ir.omidtaheri.mainpagetv.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpagetv.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpagetv.presenters.DetailsDescriptionPresenter
import ir.omidtaheri.mainpagetv.presenters.MovieCardPresenter
import ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.mainpagetv.ui.MainFragment.MainActivity
import ir.omidtaheri.uibase.GlideApp

/**
 * A wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its metadata plus related videos.
 */
class DetailsFragment : BaseDetailTvFragment<DetailViewModel>() {

    private var mSelectedMovieId: Int? = 0
    private lateinit var mDetailsBackground: DetailsSupportFragmentBackgroundController
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mRowsAdapter: ArrayObjectAdapter

    private val viewModel: DetailViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSelectedMovieId = activity?.intent?.getIntExtra(DetailsActivity.MOVIEID, 0)
        mDetailsBackground = DetailsSupportFragmentBackgroundController(this)

        if (mSelectedMovieId != null) {

            mPresenterSelector = ClassPresenterSelector()
            mRowsAdapter = ArrayObjectAdapter(mPresenterSelector)
            adapter = mRowsAdapter

            fetchDetailMovie(mSelectedMovieId)
            fetchSimilarMovies(mSelectedMovieId)
        } else {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchSimilarMovies(mSelectedMovieId: Int?) {
        viewModel.getSimilarMovies(mSelectedMovieId!!)
    }

    private fun fetchDetailMovie(mSelectedMovieId: Int?) {
        viewModel.getMovieDetail(mSelectedMovieId!!)
    }

    override fun setLiveDataObserver() {
        viewModel.detailLiveData.observe(this, Observer {
            setupDetailsOverviewRow(it)
            setupDetailsOverviewRowPresenter()
            initializeBackground(it)
            adapter.notifyItemRangeChanged(0, adapter.size())
        })

        viewModel.similarMoviesLiveData.observe(this, Observer {
            setupSimilarMovieListRow(it)
            adapter.notifyItemRangeChanged(0, adapter.size())
        })
    }

    override fun setSnackBarMessageLiveDataObserver() {
        viewModel.messageSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastMessageLiveDataObserver() {
        viewModel.messageToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setSnackBarErrorLivaDataObserver() {
        viewModel.errorSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastErrorLiveDataObserver() {
        viewModel.errorToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun configDaggerComponent() {
        DaggerDetailComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun showSnackBar(message: String) {
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    private fun initializeBackground(movieDetailUiEntity: MovieDetailUiEntity) {
        mDetailsBackground.enableParallax()

        GlideApp.with(requireActivity())
            .asBitmap()
            .load(BuildConfig.BACKDROP_URL + movieDetailUiEntity.backdropPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .error(R.drawable.default_background)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    mDetailsBackground.coverBitmap = resource
                    mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size())
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun setupDetailsOverviewRow(movieDetailUiEntity: MovieDetailUiEntity) {

        val row = DetailsOverviewRow(movieDetailUiEntity)
        row.imageDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.default_background)
        val width = convertDpToPixel(requireContext(), DETAIL_THUMB_WIDTH)
        val height = convertDpToPixel(requireContext(), DETAIL_THUMB_HEIGHT)

        GlideApp.with(requireActivity())
            .load(BuildConfig.POSTER_URL + movieDetailUiEntity.posterPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .error(R.drawable.default_background)
            .into<CustomTarget<Drawable>>(object : CustomTarget<Drawable>(width, height) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    row.imageDrawable = resource
                    mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size())
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })

        mRowsAdapter.add(row)
        mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size())
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor =
            ContextCompat.getColor(requireContext(), R.color.tv_selected_background)

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            activity, DetailsActivity.SHARED_ELEMENT_NAME
        )
        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true

        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun setupSimilarMovieListRow(multiMovieUiEntity: MultiMovieUiEntity) {

        val listRowAdapter = ArrayObjectAdapter(MovieCardPresenter())

        multiMovieUiEntity.results.forEach {
            listRowAdapter.add(it)
        }

        val header = HeaderItem(0, "Similar Movies")
        mRowsAdapter.add(ListRow(header, listRowAdapter))
        mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
        mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size())
    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    companion object {
        private const val DETAIL_THUMB_WIDTH = 274
        private const val DETAIL_THUMB_HEIGHT = 274
    }
}
