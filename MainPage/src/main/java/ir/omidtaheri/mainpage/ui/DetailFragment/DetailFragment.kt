package ir.omidtaheri.mainpage.ui.DetailFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.DetailFragmentBinding
import ir.omidtaheri.mainpage.databinding.MainFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerDetailComponent
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.ImagesGalleryViewAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.SimilarMoviesGalleryViewAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.GalleryViewAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.viewcomponents.GalleryViewer.GalleryViewer

class DetailFragment : BaseFragment(), SimilarMoviesGalleryViewAdapter.Callback {

    private lateinit var viewModel: DetailViewModel

    private var _viewbinding: DetailFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!


    lateinit var GalleryViewerImages: GalleryViewer
    lateinit var GalleryViewerSimilarMovies: GalleryViewer
    lateinit var favoriteButton: FloatingActionButton
    lateinit var GenreGroup: ChipGroup
    lateinit var MovieOverview: TextView
    lateinit var mainBackdrop: ImageView
    lateinit var rateNumber: TextView


    lateinit var adapterImages: ImagesGalleryViewAdapter
    lateinit var adapterSimilarMovies: SimilarMoviesGalleryViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        //fetchData()
    }

    private fun initRecyclerViews() {


        GalleryViewerImages.apply {

            adapterImages = ImagesGalleryViewAdapter()
            ConfigRecyclerView(
                adapterImages as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            ToLoadingState()
        }



        GalleryViewerSimilarMovies.apply {
            adapterSimilarMovies = SimilarMoviesGalleryViewAdapter()
            adapterSimilarMovies.apply {
                SetCallback(this@DetailFragment)
            }
            ConfigRecyclerView(
                adapterSimilarMovies as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            ToLoadingState()
        }


    }

    private fun fetchData(MovieId: Int) {
        viewModel.getSimilarMovies(MovieId, 0)
        viewModel.getMovieImages(MovieId)
        viewModel.getMovieDetail(MovieId)
    }


    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        GalleryViewerImages = _viewbinding!!.ImagesGalleryViewer
        GalleryViewerSimilarMovies = _viewbinding!!.SimilarMoviesGalleryViewer
        val favoriteButton = _viewbinding!!.favoriteButton
        val GenreGroup = _viewbinding!!.groupGenre
        val MovieOverview = _viewbinding!!.info
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

        viewModel.DetailLiveData.observe(this, Observer {


            MovieOverview.text = it.overview
            //mainBackdrop.setImageResource(it.backdrop_path)
            rateNumber.text = it.vote_average.toString()


        })


        viewModel.ImageListLiveData.observe(this, Observer {
            //adapterImages.addItems(it.backdrops)
            GalleryViewerImages.ToDateState()

        })

        viewModel.SimilarMoviesLiveData.observe(this, Observer {

            adapterSimilarMovies.addItems(it.results)
            GalleryViewerImages.ToDateState()
        })


        viewModel.ImagesErrorState.observe(this, Observer {
            GalleryViewerImages.ToErrorState()

        })


        viewModel.SimilarErrorState.observe(this, Observer {
            GalleryViewerSimilarMovies.ToErrorState()

        })


        viewModel.FavoritedLiveData.observe(this, Observer {


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
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun OnItemClick(MovieId: Int) {
        TODO("Not yet implemented")
    }


}