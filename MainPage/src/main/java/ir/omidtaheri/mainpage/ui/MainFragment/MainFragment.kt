package ir.omidtaheri.mainpage.ui.MainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.databinding.MainFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.GalleryViewAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.viewcomponents.GalleryViewer.GalleryViewer

class MainFragment : BaseFragment(), GalleryViewAdapter.Callback {

    private lateinit var viewModel: MainViewModel

    private var _viewbinding: MainFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var GalleryViewerTopRate: GalleryViewer
    lateinit var GalleryViewerPopular: GalleryViewer
    lateinit var GalleryViewerUpComing: GalleryViewer

    lateinit var adapterTopRate: GalleryViewAdapter
    lateinit var adapterPopular: GalleryViewAdapter
    lateinit var adapterUpComing: GalleryViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {

        GalleryViewerTopRate.apply {

            adapterTopRate = GalleryViewAdapter(MovieUiEntityComparator)
            adapterTopRate.apply {
                SetCallback(this@MainFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            ToLoadingState()
                        }

                        is LoadState.Error -> {
                            ToErrorState()
                        }

                        is LoadState.NotLoading -> {
                            ToDateState()
                        }
                    }
                }
            }
            ConfigRecyclerView(
                adapterTopRate as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
        }

        GalleryViewerPopular.apply {
            adapterPopular = GalleryViewAdapter(MovieUiEntityComparator)
            adapterPopular.apply {
                SetCallback(this@MainFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            ToLoadingState()
                        }

                        is LoadState.Error -> {
                            ToErrorState()
                        }

                        is LoadState.NotLoading -> {
                            ToDateState()
                        }
                    }
                }
            }
            ConfigRecyclerView(
                adapterPopular as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            ToLoadingState()
        }

        GalleryViewerUpComing.apply {
            adapterUpComing = GalleryViewAdapter(MovieUiEntityComparator)
            adapterUpComing.apply {
                SetCallback(this@MainFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            ToLoadingState()
                        }

                        is LoadState.Error -> {
                            ToErrorState()
                        }

                        is LoadState.NotLoading -> {
                            ToDateState()
                        }
                    }
                }
            }

            ConfigRecyclerView(
                adapterUpComing as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            ToLoadingState()
        }
    }

    private fun fetchData() {
        viewModel.getPopularMovieList()
        viewModel.getTopRatedMovieList()
        viewModel.getUpComingMovieList()
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = MainFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        GalleryViewerTopRate = _viewbinding!!.GalleryViewer1
        GalleryViewerPopular = _viewbinding!!.GalleryViewer2
        GalleryViewerUpComing = _viewbinding!!.GalleryViewer3
    }

    override fun ConfigDaggerComponent() {
        DaggerMainComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.PoularLiveData.observe(this, Observer {
            adapterPopular.submitData(lifecycle, it)
        })

        viewModel.TopRateLiveData.observe(this, Observer {
            adapterTopRate.submitData(lifecycle, it)
        })

        viewModel.UpComingLiveData.observe(this, Observer {
            adapterUpComing.submitData(lifecycle, it)
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
