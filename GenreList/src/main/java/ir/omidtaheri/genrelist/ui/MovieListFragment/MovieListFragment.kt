package ir.omidtaheri.genrelist.ui.MovieListFragment

import android.content.Intent
import android.net.Uri
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
import ir.omidtaheri.genrelist.databinding.MovieListFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerMovieListComponent
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.FooterLoadStateAdapter
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.MovieListAdapter
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModel
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class MovieListFragment : BaseFragment(), MovieListAdapter.Callback {

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var viewModel: MovieListViewModel

    private var _viewbinding: MovieListFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    lateinit var args: MovieListFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        args = MovieListFragmentArgs.fromBundle(requireArguments())
        val genreId = args.genreId
        fetchData(genreId)
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            movieListAdapter = MovieListAdapter(MovieUiEntityComparator,requireContext())

            movieListAdapter.apply {
                setCallback(this@MovieListFragment)
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

    private fun fetchData(genreId: Int) {
        viewModel.getMovieListByGenre(genreId)
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = MovieListFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = _viewbinding!!.MultiStatePage
    }

    override fun ConfigDaggerComponent() {
        DaggerMovieListComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.dataLive.observe(this, Observer {
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

    override fun onItemClick(movieId: Int) {
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("movotlin://detailmovie/" + movieId)
        )
        requireContext().startActivity(i)
    }

    override fun onStop() {
        super.onStop()
        onDestroyGlide()
    }
}
