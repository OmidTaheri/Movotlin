package ir.omidtaheri.androidbase

import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import javax.inject.Inject

abstract class BaseBrowseTvFragment<T : ViewModel> : BrowseSupportFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelAssistedFactory<T>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configDaggerComponent()
        setLivaDataObserver()
    }

    private fun setLivaDataObserver() {
        setToastErrorLiveDataObserver()
        setSnackBarErrorLivaDataObserver()
        setToastMessageLiveDataObserver()
        setSnackBarMessageLiveDataObserver()
        setLiveDataObserver()
    }

    abstract fun setLiveDataObserver()
    abstract fun setSnackBarMessageLiveDataObserver()
    abstract fun setToastMessageLiveDataObserver()
    abstract fun setSnackBarErrorLivaDataObserver()
    abstract fun setToastErrorLiveDataObserver()
    abstract fun configDaggerComponent()
    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)
    abstract fun showDialog(message: String)
}
