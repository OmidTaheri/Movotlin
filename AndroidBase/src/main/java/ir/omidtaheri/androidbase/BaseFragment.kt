package ir.omidtaheri.androidbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return InflateViewBinding(inflater, container)
    }

    abstract fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiComponent()
        ConfigDaggerComponent()
        SetViewModel()
        SetLivaDataObserver()
    }

    abstract fun bindUiComponent()

    private fun SetLivaDataObserver() {
        setLoadingLiveDataObserver()
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

    abstract fun setLoadingLiveDataObserver()

    abstract fun ConfigDaggerComponent()

    abstract fun SetViewModel()

    abstract fun showLoading(show: Boolean)

    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)
    abstract fun showDialog(message: String)
}
