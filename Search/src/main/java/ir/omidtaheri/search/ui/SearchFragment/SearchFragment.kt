package ir.omidtaheri.search.ui.SearchFragment

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
import ir.omidtaheri.search.R
import ir.omidtaheri.search.databinding.SearchFragmentBinding
import ir.omidtaheri.search.di.components.DaggerSearchComponent
import ir.omidtaheri.search.entity.MovieUiEntity
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModel

class SearchFragment : BaseFragment<List<MovieUiEntity>>(){
    private var _viewbinding: SearchFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!


    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = SearchFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {

    }

    override fun ConfigDaggerComponent() {
        DaggerSearchComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
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