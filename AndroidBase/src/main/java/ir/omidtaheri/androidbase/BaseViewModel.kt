package ir.omidtaheri.androidbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.omidtaheri.androidbase.singleLiveData.SingleLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val applicationClass: Application

    init {
        applicationClass = application
    }

    protected val _isLoading: MutableLiveData<Boolean>
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _errorSnackBar: SingleLiveData<String>
    val errorSnackBar: LiveData<String>
        get() = _errorSnackBar

    protected val _errorToast: SingleLiveData<String>
    val errorToast: LiveData<String>
        get() = _errorToast

    protected val _messageSnackBar: SingleLiveData<String>
    val messageSnackBar: LiveData<String>
        get() = _messageSnackBar

    protected val _messageToast: SingleLiveData<String>
    val messageToast: LiveData<String>
        get() = _messageToast


    init {
        _isLoading = MutableLiveData()
        _errorSnackBar = SingleLiveData()
        _errorToast = SingleLiveData()
        _messageSnackBar = SingleLiveData()
        _messageToast = SingleLiveData()
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
