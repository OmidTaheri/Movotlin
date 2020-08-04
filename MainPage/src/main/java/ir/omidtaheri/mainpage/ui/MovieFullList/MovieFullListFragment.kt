package ir.omidtaheri.mainpage.ui.MovieFullList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.databinding.MovieFullListFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMovieFullListComponent
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.FooterLoadStateAdapter
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.MovieFullListAdapter
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel.MovieFullListViewModel
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage


class MovieFullListFragment : BaseFragment(), MovieFullListAdapter.Callback {

    private lateinit var movieListAdapter: MovieFullListAdapter
    private lateinit var viewModel: MovieFullListViewModel


    private var _viewbinding: MovieFullListFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        fetchData(1)
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            movieListAdapter = MovieFullListAdapter(MovieUiEntityComparator)

            movieListAdapter.apply {
                SetCallback(this@MovieFullListFragment)
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

                withLoadStateFooter(
                    FooterLoadStateAdapter((::retry))
                )
            }



            ConfigRecyclerView(
                movieListAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )

        }
    }


    private fun fetchData(CategoryId: Int) {
        when (CategoryId) {
            1 -> viewModel.getTopRatedMovieList()
            2 -> viewModel.getPopularMovieList()
            3 -> viewModel.getUpComingMovieList()
        }


    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = MovieFullListFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = _viewbinding!!.MultiStatePage
    }

    override fun ConfigDaggerComponent() {
        DaggerMovieFullListComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieFullListViewModel::class.java)
    }


    override fun setDataLiveObserver() {

        viewModel.PoularLiveData.observe(this, Observer {
            movieListAdapter.submitData(lifecycle, it)

        })

        viewModel.TopRateLiveData.observe(this, Observer {
            movieListAdapter.submitData(lifecycle, it)

        })


        viewModel.UpComingLiveData.observe(this, Observer {
            movieListAdapter.submitData(lifecycle, it)

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