package ir.omidtaheri.androidbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.omidtaheri.androidbase.singleLiveData.SingleLiveData

open class BaseAndroidViewModel(
    private val mApplication: Application,
    private val state: SavedStateHandle
) : AndroidViewModel(mApplication) {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _errorSnackBar: SingleLiveData<String> = SingleLiveData()
    val errorSnackBar: LiveData<String>
        get() = _errorSnackBar

    protected val _errorToast: SingleLiveData<String> = SingleLiveData()
    val errorToast: LiveData<String>
        get() = _errorToast

    protected val _messageSnackBar: SingleLiveData<String> = SingleLiveData()
    val messageSnackBar: LiveData<String>
        get() = _messageSnackBar

    protected val _messageToast: SingleLiveData<String> = SingleLiveData()
    val messageToast: LiveData<String>
        get() = _messageToast


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
