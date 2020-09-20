package ir.omidtaheri.androidbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.omidtaheri.androidbase.singleLiveData.SingleLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val ApplicationClass: Application

    init {
        ApplicationClass = application
    }

    protected val _isLoading: MutableLiveData<Boolean>
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _ErrorSnackBar: SingleLiveData<String>
    val ErrorSnackBar: LiveData<String>
        get() = _ErrorSnackBar

    protected val _ErrorToast: SingleLiveData<String>
    val ErrorToast: LiveData<String>
        get() = _ErrorToast

    protected val _MessageSnackBar: SingleLiveData<String>
    val MessageSnackBar: LiveData<String>
        get() = _MessageSnackBar

    protected val _MessageToast: SingleLiveData<String>
    val MessageToast: LiveData<String>
        get() = _MessageToast

//    protected val _DataLive: MutableLiveData<DataLiveType>
//    val DataLive: LiveData<DataLiveType>
//        get() = _DataLive

    init {
        _isLoading = MutableLiveData()
        _ErrorSnackBar = SingleLiveData()
        _ErrorToast = SingleLiveData()
        _MessageSnackBar = SingleLiveData()
        _MessageToast = SingleLiveData()
        // _DataLive = MutableLiveData()
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {

        compositeDisposable.add(disposable)
    }

    fun deleteDisposable(disposable: Disposable) {

        compositeDisposable.delete(disposable)
    }


    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }
}
