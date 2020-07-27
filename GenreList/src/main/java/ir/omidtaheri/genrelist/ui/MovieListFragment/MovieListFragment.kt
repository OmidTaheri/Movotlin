package ir.omidtaheri.genrelist.ui.MovieListFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.genrelist.R
import ir.omidtaheri.genrelist.databinding.MovieListFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerMovieListComponent
import ir.omidtaheri.genrelist.entity.MultiMovieUiEntity
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModel

class MovieListFragment : BaseFragment<MultiMovieUiEntity>() {


    private var _viewbinding: MovieListFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!


    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = MovieListFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {

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

        viewModel.DataLive.observe(this, Observer {


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


}