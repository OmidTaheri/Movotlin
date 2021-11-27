package ir.omidtaheri.detailpage.ui.DetailFragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

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
    private var stateImagesRecyclerview: Parcelable? = null
    private var stateSimilarMoviesRecyclerview: Parcelable? = null
    private var isEnableAnimation = true

    private val viewModel: DetailViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            isEnableAnimation = false
            stateImagesRecyclerview =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("images")
            stateSimilarMoviesRecyclerview =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("similarMovies")

        } else {

            val recyclerViewsState = viewModel.restoreStateOfRecyclerViews()

            if (recyclerViewsState.size > 0) {
                isEnableAnimation = false

                recyclerViewsState[0]?.let {
                    stateImagesRecyclerview = it
                }

                recyclerViewsState[1]?.let {
                    stateSimilarMoviesRecyclerview = it
                }
            }
        }

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

            if (isEnableAnimation)
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
            if (isEnableAnimation)
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

        viewLifecycleOwner.lifecycleScope.launch {
            whenStarted {
                favoriteButton.clicksFlow().collectLatest {
                    val item = FavoritedMovieUiEntity(
                        backdropPath,
                        id,
                        posterPath,
                        title,
                        voteAverage,
                        !isFavorite
                    )

                    if (!isFavorite) {
                        viewModel.setFavoriteMovie(item)
                    } else {
                        viewModel.setUnFavoriteMovie(item)
                    }
                }
            }
        }

    }

    fun View.clicksFlow(): Flow<View?> = callbackFlow<View?> {

        val clickListener = View.OnClickListener {
            offer(it)
        }
        setOnClickListener(clickListener)
        awaitClose { setOnClickListener(null) }
    }.debounce(1000)

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
                    if (isEnableAnimation) {
                        tagline.alpha = 0F
                        tagline.animate().alpha(1F).setDuration(1000).start()
                    }
                }
            }

            movieOverview.visibility = View.GONE

            it.overview?.let {

                if (it.length > 0) {
                    movieOverview.text = it
                    movieOverview.visibility = View.VISIBLE
                    if (isEnableAnimation) {
                        movieOverview.alpha = 0F
                        movieOverview.animate().alpha(1F).setDuration(1000).start()
                    }
                }
            }

            rateNumber.text = it.voteAverage.toString()

            if (isEnableAnimation)
                rateNumber.animate().rotation(720F).setDuration(2000).start()

            genreGroup.removeAllViews()
            for (index in it.genres) {
                val chip = Chip(context)
                chip.text = index.name
                genreGroup.addView(chip)
            }

            if (isEnableAnimation) {
                genreGroup.alpha = 0F
                genreGroup.animate().alpha(1F).setDuration(1000).start()
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

            if (it.backdrops.isNotEmpty()) {
                it.backdrops.forEach { item ->
                    adapterImages.addItem(item)
                }
                galleryViewerImages.toDateState()

                stateImagesRecyclerview?.let {
                    galleryViewerImages.getRecyclerView().layoutManager?.onRestoreInstanceState(
                        it
                    )
                    stateImagesRecyclerview = null
                }

            } else {
                titleImages.visibility = View.GONE
                galleryViewerImages.visibility = View.GONE
            }
        })

        viewModel.similarMoviesLiveData.observe(this, Observer {
            adapterSimilarMovies.submitData(viewLifecycleOwner.lifecycle, it)

            stateSimilarMoviesRecyclerview?.let {
                galleryViewerSimilarMovies.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                stateSimilarMoviesRecyclerview = null
            }


            if (adapterSimilarMovies.itemCount == 0) {
                titleSimilar.visibility = View.GONE
                galleryViewerSimilarMovies.visibility = View.GONE
            }else{
                galleryViewerSimilarMovies.toDateState()
            }


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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val imagesRecyclerState =
            galleryViewerImages.getRecyclerView().layoutManager?.onSaveInstanceState()

        val similarMoviesRecyclerState =
            galleryViewerSimilarMovies.getRecyclerView().layoutManager?.onSaveInstanceState()

        outState.putParcelable("images", imagesRecyclerState)
        outState.putParcelable("similarMovies", similarMoviesRecyclerState)

    }

    override fun onDestroyView() {

        val imagesRecyclerState =
            galleryViewerImages.getRecyclerView().layoutManager?.onSaveInstanceState()

        val similarMoviesRecyclerState =
            galleryViewerSimilarMovies.getRecyclerView().layoutManager?.onSaveInstanceState()

        viewModel.saveStateOfRecyclerViews(
            imagesRecyclerState as LinearLayoutManager.SavedState?,
            similarMoviesRecyclerState as LinearLayoutManager.SavedState?
        )

        onDestroyGlide()
        super.onDestroyView()
        viewBinding = null
    }

    override fun onItemClick(movieId: Int) {
        val action = DetailFragmentDirections.actionDetailFragmentSelf(movieId)
        findNavController().navigate(action)
    }
}
