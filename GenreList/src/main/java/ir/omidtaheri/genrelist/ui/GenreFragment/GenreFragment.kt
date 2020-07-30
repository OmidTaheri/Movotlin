package ir.omidtaheri.genrelist.ui.GenreFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.genrelist.R
import ir.omidtaheri.genrelist.databinding.GenreFragmentBinding
import ir.omidtaheri.genrelist.databinding.MovieListFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerGenreComponent
import ir.omidtaheri.genrelist.entity.GenreUiEntity
import ir.omidtaheri.genrelist.entity.MultiMovieUiEntity
import ir.omidtaheri.genrelist.ui.GenreFragment.adapters.GenreListAdapter
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModel
import ir.omidtaheri.genrelist.ui.MovieListFragment.adapters.MovieListAdapter
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModel
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class GenreFragment : BaseFragment(), GenreListAdapter.Callback {

    private lateinit var genreListAdapter: GenreListAdapter
    private lateinit var viewModel: GenreViewModel


    private var _viewbinding: GenreFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!


    lateinit var multiStatePage: MultiStatePage


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            genreListAdapter = GenreListAdapter()
            genreListAdapter.SetCallback(this@GenreFragment)
            ConfigRecyclerView(
                genreListAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            )
            ToLoadingState()
        }
    }


    private fun fetchData() {
        viewModel.getMovieListByGenre()
    }


    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = GenreFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = _viewbinding!!.MultiStatePage
    }

    override fun ConfigDaggerComponent() {
        DaggerGenreComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenreViewModel::class.java)
    }


    override fun setDataLiveObserver() {

        viewModel.DataLive.observe(this, Observer {
            genreListAdapter.addItems(it)
        })

        viewModel.GenreErrorState.observe(this, Observer {
            multiStatePage.ToErrorState()
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