package ir.omidtaheri.androidbase

import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseBrowseTvFragment : BrowseSupportFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configDaggerComponent()
        setViewModel()
        setLivaDataObserver()
    }

    private fun setLivaDataObserver() {
        setToastErrorLiveDataObserver()
        setSnackBarErrorLivaDataObserver()
        setToastMessageLiveDataObserver()
        setSnackBarMessageLiveDataObserver()
        setDataLiveObserver()
    }

    abstract fun setDataLiveObserver()
    abstract fun setSnackBarMessageLiveDataObserver()
    abstract fun setToastMessageLiveDataObserver()
    abstract fun setSnackBarErrorLivaDataObserver()
    abstract fun setToastErrorLiveDataObserver()
    abstract fun configDaggerComponent()
    abstract fun setViewModel()
    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)
    abstract fun showDialog(message: String)
}
