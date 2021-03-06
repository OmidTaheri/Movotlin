package ir.omidtaheri.mainpage.ui.MovieFullList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.databinding.MovieFullListFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMovieFullListComponent
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.FooterLoadStateAdapter
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.MovieFullListAdapter
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel.MovieFullListViewModel
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class MovieFullListFragment : BaseFragment(), MovieFullListAdapter.Callback {

    private lateinit var movieListAdapter: MovieFullListAdapter
    private lateinit var viewModel: MovieFullListViewModel

    private var _viewbinding: MovieFullListFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    lateinit var args: MovieFullListFragmentArgs

    var CategoryId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        args = MovieFullListFragmentArgs.fromBundle(requireArguments())
        CategoryId = args.categoryId
        fetchData(CategoryId)
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            movieListAdapter = MovieFullListAdapter(MovieUiEntityComparator, requireContext())

            movieListAdapter.apply {
                setCallback(this@MovieFullListFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {
                                when (CategoryId) {
                                    1 -> viewModel.getTopRatedMovieList()
                                    2 -> viewModel.getPopularMovieList()
                                    3 -> viewModel.getUpComingMovieList()
                                }
                            })
                        }

                        is LoadState.NotLoading -> {
                            toDateState()
                        }
                    }
                }

                withLoadStateFooter(
                    FooterLoadStateAdapter((::retry))
                )
            }

            configRecyclerView(
                movieListAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )
        }
    }

    private fun fetchData(categoryId: Int) {
        when (categoryId) {
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

        viewModel.poularLiveData.observe(this, Observer {
            movieListAdapter.submitData(lifecycle, it)
        })

        viewModel.topRateLiveData.observe(this, Observer {
            movieListAdapter.submitData(lifecycle, it)
        })

        viewModel.upComingLiveData.observe(this, Observer {
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

    override fun onStop() {
        super.onStop()
        onDestroyGlide()
    }

    override fun onItemClick(MovieId: Int) {
        TODO("Not yet implemented")
    }
}
