package ir.omidtaheri.androidbase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class BaseViewModel : ViewModel() {


    private val _isLoading: MutableLiveData<Boolean>
    val isLoading
        get() = _isLoading


    private val _ErrorString: MutableLiveData<String>
    val ErrorString
        get() = _ErrorString


    private val _ErrorResId: MutableLiveData<Int>
    val ErrorResId
        get() = _ErrorResId


    private val _MessageString: MutableLiveData<String>
    val MessageString
        get() = _MessageString


    private val _MessageResId: MutableLiveData<Int>
    val MessageResId
        get() = _MessageResId


    init {
        _isLoading = MutableLiveData()
        _ErrorString = MutableLiveData()
        _ErrorResId = MutableLiveData()
        _MessageString = MutableLiveData()
        _MessageResId = MutableLiveData()
    }


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {

        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }
}