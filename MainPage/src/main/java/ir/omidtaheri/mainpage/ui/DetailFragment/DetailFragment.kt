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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.databinding.DetailFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerDetailComponent
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.ImagesGalleryViewAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.SimilarMoviesGalleryViewAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.mainpage.ui.MovieFullList.MovieFullListFragmentArgs
import ir.omidtaheri.uibase.LoadBackdrop
import ir.omidtaheri.uibase.LoadPoster
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

    lateinit var adapterImages: ImagesGalleryViewAdapter
    lateinit var adapterSimilarMovies: SimilarMoviesGalleryViewAdapter

    lateinit var args: DetailFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        args = DetailFragmentArgs.fromBundle(requireArguments())
        val movieId = args.movieId
        fetchData(movieId)
    }

    private fun initRecyclerViews() {

        galleryViewerImages.apply {

            adapterImages = ImagesGalleryViewAdapter()

            configRecyclerView(
                adapterImages as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            toLoadingState()
        }

        galleryViewerSimilarMovies.apply {
            adapterSimilarMovies = SimilarMoviesGalleryViewAdapter(MovieUiEntityComparator)
            adapterSimilarMovies.apply {
                setCallback(this@DetailFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState()
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
        val favoriteButton = _viewbinding!!.favoriteButton
        val genreGroup = _viewbinding!!.groupGenre
        val movieOverview = _viewbinding!!.info
        val mainBackdrop = _viewbinding!!.mainBackdrop
        val rateNumber = _viewbinding!!.rateNumber
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
            it.posterPath?.let { it1 -> mainBackdrop.LoadPoster(it1) }
                ?: it.backdropPath?.let { it1 -> mainBackdrop.LoadBackdrop(it1) }
            rateNumber.text = it.voteAverage.toString()
        })

        viewModel.imageListLiveData.observe(this, Observer {
            it.posters.forEach {
                adapterImages.addItem(it)
            }
        })

        viewModel.similarMoviesLiveData.observe(this, Observer {
            adapterSimilarMovies.submitData(lifecycle, it)
        })

        viewModel.imagesErrorState.observe(this, Observer {
            galleryViewerImages.toErrorState()
        })

        viewModel.favoritedLiveData.observe(this, Observer {
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

    override fun onItemClick(MovieId: Int) {
        TODO("Not yet implemented")
    }
}
