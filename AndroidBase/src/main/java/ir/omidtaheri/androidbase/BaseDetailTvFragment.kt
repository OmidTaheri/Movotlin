package ir.omidtaheri.androidbase

import android.os.Bundle
import androidx.leanback.app.DetailsSupportFragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseDetailTvFragment : DetailsSupportFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConfigDaggerComponent()
        SetViewModel()
        SetLivaDataObserver()
    }

    private fun SetLivaDataObserver() {
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
    abstract fun ConfigDaggerComponent()
    abstract fun SetViewModel()
    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)
    abstract fun showDialog(message: String)
}
