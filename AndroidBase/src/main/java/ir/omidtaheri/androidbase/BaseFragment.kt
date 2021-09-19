package ir.omidtaheri.androidbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import javax.inject.Inject

abstract class BaseFragment<T : ViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelAssistedFactory<T>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateViewBinding(inflater, container)
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiComponent()
        configDaggerComponent()
        setViewModel()
        setLivaDataObserver()
    }

    abstract fun bindUiComponent()

    private fun setLivaDataObserver() {
        setLoadingLiveDataObserver()
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

    abstract fun setLoadingLiveDataObserver()

    abstract fun configDaggerComponent()

    abstract fun setViewModel()

    abstract fun showLoading(show: Boolean)

    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)
    abstract fun showDialog(message: String)
}
