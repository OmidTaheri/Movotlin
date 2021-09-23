package ir.omidtaheri.genrelist.ui.MovieListFragment

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.genrelist.R
import ir.omidtaheri.genrelist.databinding.MovieListFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerMovieListComponent
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.FooterLoadStateAdapter
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.MovieListAdapter
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModel
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class MovieListFragment : BaseFragment<MovieListViewModel>(), MovieListAdapter.Callback {

    private lateinit var movieListAdapter: MovieListAdapter
    private var viewBinding: MovieListFragmentBinding? = null
    private lateinit var multiStatePage: MultiStatePage
    private lateinit var args: MovieListFragmentArgs
    private var genreId: Int = 0

    private val viewModel: MovieListViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        args = MovieListFragmentArgs.fromBundle(requireArguments())
        genreId = args.genreId
        fetchData(genreId)
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            movieListAdapter = MovieListAdapter(MovieUiEntityComparator, requireContext())

            movieListAdapter.apply {
                setCallback(this@MovieListFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {
                                viewModel.getMovieListByGenre(genreId)
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
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down_long_time)
        }
    }

    private fun fetchData(genreId: Int) {
        viewModel.getMovieListByGenre(genreId)
    }

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = MovieListFragmentBinding.inflate(inflater, container, false)
        val view = viewBinding!!.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = viewBinding!!.MultiStatePage
    }

    override fun configDaggerComponent() {
        DaggerMovieListComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun setLiveDataObserver() {
        viewModel.dataLive.observe(this, Observer {
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

    override fun onItemClick(movieId: Int) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }


}
