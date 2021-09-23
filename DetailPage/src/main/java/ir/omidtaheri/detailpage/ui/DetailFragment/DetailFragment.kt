package ir.omidtaheri.detailpage.ui.DetailFragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.detailpage.R
import ir.omidtaheri.detailpage.databinding.DetailFragmentBinding
import ir.omidtaheri.detailpage.di.components.DaggerDetailComponent
import ir.omidtaheri.detailpage.entity.FavoritedMovieUiEntity
import ir.omidtaheri.detailpage.ui.DetailFragment.adapters.ImagesGalleryViewAdapter
import ir.omidtaheri.detailpage.ui.DetailFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.detailpage.ui.DetailFragment.adapters.SimilarMoviesGalleryViewAdapter
import ir.omidtaheri.detailpage.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.uibase.LoadMainBackdrop
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.GalleryViewer.GalleryViewer

class DetailFragment : BaseFragment<DetailViewModel>(), SimilarMoviesGalleryViewAdapter.Callback {

    private var viewBinding: DetailFragmentBinding? = null
    private lateinit var galleryViewerImages: GalleryViewer
    private lateinit var galleryViewerSimilarMovies: GalleryViewer
    private lateinit var favoriteButton: FloatingActionButton
    private lateinit var genreGroup: ChipGroup
    private lateinit var movieOverview: TextView
    private lateinit var mainBackdrop: ImageView
    private lateinit var rateNumber: TextView
    private lateinit var tagline: TextView
    private lateinit var toolbar: CollapsingToolbarLayout
    private lateinit var titleSimilar: TextView
    private lateinit var titleImages: TextView
    private lateinit var adapterImages: ImagesGalleryViewAdapter
    private lateinit var adapterSimilarMovies: SimilarMoviesGalleryViewAdapter
    private lateinit var args: DetailFragmentArgs
    private var isFavorite = false
    private var savedStateMainbackdrop: String? = null
    private var savedStateMainposter: String? = null
    private var movieId: Int = 0

    private val viewModel: DetailViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        args = DetailFragmentArgs.fromBundle(requireArguments())
        movieId = args.movieId
        checkFavoriteStatus(movieId)
        fetchData(movieId)
    }

    private fun initRecyclerViews() {

        galleryViewerImages.apply {

            adapterImages = ImagesGalleryViewAdapter(requireContext())

            configRecyclerView(
                adapterImages as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            )
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
            toLoadingState()
        }

        galleryViewerSimilarMovies.apply {
            adapterSimilarMovies =
                SimilarMoviesGalleryViewAdapter(MovieUiEntityComparator, requireContext())
            adapterSimilarMovies.apply {
                setCallback(this@DetailFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {
                                viewModel.getSimilarMovies(movieId)
                            })
                        }

                        is LoadState.NotLoading -> {
                            toDateState()
                        }
                    }
                }
            }
            configRecyclerView(
                adapterSimilarMovies as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            )
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
        }
    }

    private fun checkFavoriteStatus(movieId: Int) {
        viewModel.checkFavoriteStatus(movieId)
    }

    private fun fetchData(movieId: Int) {
        viewModel.getSimilarMovies(movieId)
        viewModel.getMovieImages(movieId)
        viewModel.getMovieDetail(movieId)
    }

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = viewBinding!!.root
        return view
    }

    override fun bindUiComponent() {
        galleryViewerImages = viewBinding!!.ImagesGalleryViewer
        galleryViewerSimilarMovies = viewBinding!!.SimilarMoviesGalleryViewer
        favoriteButton = viewBinding!!.favoriteButton
        genreGroup = viewBinding!!.groupGenre
        movieOverview = viewBinding!!.info
        mainBackdrop = viewBinding!!.mainBackdrop
        rateNumber = viewBinding!!.rateNumber
        tagline = viewBinding!!.rateText
        toolbar = viewBinding!!.mainCollapsing
        titleSimilar = viewBinding!!.titleSimilar
        titleImages = viewBinding!!.titleImages
    }

    private fun setFabListner(
        backdropPath: String?,
        id: Int,
        posterPath: String?,
        title: String,
        voteAverage: Double
    ) {

        viewModel.setFavoriteSubjectObserver()

        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite

            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            viewModel.favoriteSubject.onNext(
                FavoritedMovieUiEntity(
                    backdropPath,
                    id,
                    posterPath,
                    title,
                    voteAverage,
                    isFavorite
                )
            )
        }
    }

    override fun onStop() {
        super.onStop()
        onDestroyGlide()
    }

    override fun configDaggerComponent() {
        DaggerDetailComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun setLiveDataObserver() {

        viewModel.detailLiveData.observe(this, Observer {

            toolbar.title = it.title

            it.backdropPath?.let { it1 ->
                savedStateMainbackdrop = it1
                mainBackdrop.LoadMainBackdrop(it1, requireContext())
            }

            tagline.visibility = View.GONE
            it.tagline?.let {

                if (it.length > 0) {
                    tagline.text = it
                    tagline.visibility = View.VISIBLE
                    tagline.alpha = 0F
                    tagline.animate().alpha(1F).setDuration(1000).start()
                }
            }

            movieOverview.visibility = View.GONE

            it.overview?.let {

                if (it.length > 0) {
                    movieOverview.text = it
                    movieOverview.visibility = View.VISIBLE
                    movieOverview.alpha = 0F
                    movieOverview.animate().alpha(1F).setDuration(1000).start()
                }
            }

            rateNumber.text = it.voteAverage.toString()
            rateNumber.animate().rotation(720F).setDuration(2000).start()

            genreGroup.removeAllViews()
            for (index in it.genres) {
                val chip = Chip(context)
                chip.text = index.name
                genreGroup.addView(chip)
            }
            genreGroup.alpha = 0F
            genreGroup.animate().alpha(1F).setDuration(1000).start()

            setFabListner(
                it.backdropPath,
                it.id,
                it.posterPath,
                it.title,
                it.voteAverage
            )
        })

        viewModel.imageListLiveData.observe(this, Observer {

            if (it.backdrops.isNotEmpty()) {
                it.backdrops.forEach { item ->
                    adapterImages.addItem(item)
                }
                galleryViewerImages.toDateState()
            } else {
                titleImages.visibility = View.GONE
                galleryViewerImages.visibility = View.GONE
            }
        })

        viewModel.similarMoviesLiveData.observe(this, Observer {
            adapterSimilarMovies.submitData(lifecycle, it)

            val handler = Handler()
            val runnable: Runnable = Runnable {
                if (adapterSimilarMovies.itemCount == 0) {
                    titleSimilar.visibility = View.GONE
                    galleryViewerSimilarMovies.visibility = View.GONE
                }
            }
            handler.postDelayed(runnable, 5000)
        })

        viewModel.imagesErrorState.observe(this, Observer {
            galleryViewerImages.toErrorState(View.OnClickListener {
                galleryViewerImages.toLoadingState()
                viewModel.getMovieImages(movieId)
            })
        })

        viewModel.favoritedLiveData.observe(this, Observer {
            isFavorite = it

            if (it) {
                favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
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

    override fun setLoadingLiveDataObserver() {
        viewModel.isLoading.observe(this, Observer {
            showLoading(it)
        })
    }

    override fun showLoading(show: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(viewBinding!!.root, message, BaseTransientBottomBar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_action_text) {
                viewModel.getMovieDetail(movieId)
            }.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onItemClick(movieId: Int) {
        val action = DetailFragmentDirections.actionDetailFragmentSelf(movieId)
        findNavController().navigate(action)
    }
}
