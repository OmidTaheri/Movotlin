package ir.omidtaheri.favorite.ui.FavoriteFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.favorite.databinding.FavoriteFragmentBinding
import ir.omidtaheri.favorite.di.components.DaggerFavoriteComponent
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModel

class FavoriteFragment : BaseFragment<List<FavoritedMovieUiEntity>>() {


    private var _viewbinding: FavoriteFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!


    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = FavoriteFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {

    }

    override fun ConfigDaggerComponent() {
        DaggerFavoriteComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
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