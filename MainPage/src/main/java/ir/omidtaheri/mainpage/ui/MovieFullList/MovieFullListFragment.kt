package ir.omidtaheri.mainpage.ui.MovieFullList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.databinding.MovieFullListFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMovieFullListComponent
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.FooterLoadStateAdapter
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.MovieFullListAdapter
import ir.omidtaheri.mainpage.ui.MovieFullList.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel.MovieFullListViewModel
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class MovieFullListFragment : BaseFragment<MovieFullListViewModel>(),
    MovieFullListAdapter.Callback {

    private lateinit var movieListAdapter: MovieFullListAdapter
    private var viewBinding: MovieFullListFragmentBinding? = null
    private lateinit var multiStatePage: MultiStatePage
    private lateinit var args: MovieFullListFragmentArgs
    private var categoryId: Int = 0

    private val viewModel: MainViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        args = MovieFullListFragmentArgs.fromBundle(requireArguments())
        categoryId = args.categoryId
        fetchData(categoryId)
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
                                when (categoryId) {
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

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = MovieFullListFragmentBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun bindUiComponent() {
        multiStatePage = viewBinding!!.MultiStatePage
    }

    override fun configDaggerComponent() {
        DaggerMovieFullListComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun setLiveDataObserver() {

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
        Snackbar.make(viewBinding!!.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        super.onStop()
        onDestroyGlide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


    override fun onItemClick(MovieId: Int) {
        TODO("Not yet implemented")
    }
}
