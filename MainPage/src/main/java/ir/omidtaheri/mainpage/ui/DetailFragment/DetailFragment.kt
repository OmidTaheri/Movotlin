package ir.omidtaheri.mainpage.ui.DetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.DetailFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerDetailComponent
import ir.omidtaheri.mainpage.entity.FavoritedMovieUiEntity
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.ImagesGalleryViewAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.SimilarMoviesGalleryViewAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.uibase.LoadBackdrop
import ir.omidtaheri.uibase.LoadPoster
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.GalleryViewer.GalleryViewer

class DetailFragment : BaseFragment(), SimilarMoviesGalleryViewAdapter.Callback {

    private lateinit var viewModel: DetailViewModel

    private var _viewbinding: DetailFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var galleryViewerImages: GalleryViewer
    lateinit var galleryViewerSimilarMovies: GalleryViewer
    lateinit var favoriteButton: FloatingActionButton
    lateinit var genreGroup: ChipGroup
    lateinit var movieOverview: TextView
    lateinit var mainBackdrop: ImageView
    lateinit var rateNumber: TextView
    lateinit var tagline: TextView
    lateinit var toolbar: CollapsingToolbarLayout

    lateinit var adapterImages: ImagesGalleryViewAdapter
    lateinit var adapterSimilarMovies: SimilarMoviesGalleryViewAdapter

    lateinit var args: DetailFragmentArgs

    var isFavorite = false

    var SavedState_mainbackdrop: String? = null
    var SavedState_mainposter: String? = null

    var movieId: Int = 0

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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
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

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        galleryViewerImages = _viewbinding!!.ImagesGalleryViewer
        galleryViewerSimilarMovies = _viewbinding!!.SimilarMoviesGalleryViewer
        favoriteButton = _viewbinding!!.favoriteButton
        genreGroup = _viewbinding!!.groupGenre
        movieOverview = _viewbinding!!.info
        mainBackdrop = _viewbinding!!.mainBackdrop
        rateNumber = _viewbinding!!.rateNumber
        tagline = _viewbinding!!.rateText
        toolbar = _viewbinding!!.mainCollapsing
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


    override fun ConfigDaggerComponent() {
        DaggerDetailComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.detailLiveData.observe(this, Observer {

            movieOverview.text = it.overview
            toolbar.title = it.title

            it.backdropPath?.let { it1 ->
                SavedState_mainbackdrop = it1
                mainBackdrop.LoadBackdrop(it1, requireContext())
            }
                ?: it.posterPath?.let { it1 ->
                    SavedState_mainposter = it1
                    mainBackdrop.LoadPoster(it1, requireContext())
                }


            rateNumber.text = it.voteAverage.toString()
            tagline.text = it.tagline

            for (index in it.genres) {
                val chip = Chip(context)
                chip.text = index.name
                genreGroup.addView(chip)
            }
            setFabListner(
                it.backdropPath,
                it.id,
                it.posterPath,
                it.title,
                it.voteAverage
            )
        })

        viewModel.imageListLiveData.observe(this, Observer {
            it.backdrops.forEach {
                adapterImages.addItem(it)
            }
            galleryViewerImages.toDateState()
        })

        viewModel.similarMoviesLiveData.observe(this, Observer {
            adapterSimilarMovies.submitData(lifecycle, it)
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
        viewModel.MessageSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastMessageLiveDataObserver() {
        viewModel.MessageToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setSnackBarErrorLivaDataObserver() {
        viewModel.ErrorSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastErrorLiveDataObserver() {
        viewModel.ErrorToast.observe(this, Observer {
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
        Snackbar.make(viewbinding.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun onItemClick(movieId: Int) {
        val action = DetailFragmentDirections.actionDetailFragmentSelf(movieId)
        findNavController().navigate(action)
    }
}
