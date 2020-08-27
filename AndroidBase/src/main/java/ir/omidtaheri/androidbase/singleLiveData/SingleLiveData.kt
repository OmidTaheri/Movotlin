package ir.omidtaheri.androidbase.singleLiveData

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> : MutableLiveData<T>() {

    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer<T> { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        this.value = null
    }
}
