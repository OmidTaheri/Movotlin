package ir.omidtaheri.mainpagetv.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ir.omidtaheri.mainpagetv.R
import ir.omidtaheri.uibase.GlideApp
import java.lang.ref.WeakReference
import java.util.Timer
import java.util.TimerTask

class GlideBackgroundManager(activity: Activity) {

    private val BACKGROUND_UPDATE_DELAY = 300
    private val mActivityWeakReference: WeakReference<Activity>
    private var mBackgroundManager: BackgroundManager
    private val mHandler = Handler(Looper.getMainLooper())
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics

    init {
        mActivityWeakReference = WeakReference(activity)
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity.window)
        mDefaultBackground =
            ContextCompat.getDrawable(activity, R.drawable.default_background)
        mMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(mMetrics)
    }

    fun loadImageBackground(imageUrl: String) {
        mBackgroundUri = imageUrl
        startBackgroundTimer()
    }

    fun setBackground(drawable: Drawable) {
        if (mBackgroundManager != null) {
            if (!mBackgroundManager.isAttached()) {
                mBackgroundManager.attach(mActivityWeakReference.get()?.window)
            }
            mBackgroundManager.drawable = drawable
        }
    }

    private fun startBackgroundTimer() {
        cancelTimer()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(
            UpdateBackgroundTask(),
            BACKGROUND_UPDATE_DELAY.toLong()
        )
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHandler.post { updateBackground() }
        }
    }

    private fun updateBackground() {

        if (mActivityWeakReference.get() != null) {
            val width = mMetrics.widthPixels
            val height = mMetrics.heightPixels
            GlideApp.with(mActivityWeakReference.get() as Activity)
                .load("http://image.tmdb.org/t/p/w1280/" + mBackgroundUri)
                .centerCrop()
                .error(mDefaultBackground)
                .into<CustomTarget<Drawable>>(
                    object : CustomTarget<Drawable>(width, height) {

                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            setBackground(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
        }
    }

    private fun cancelTimer() {
        mBackgroundTimer?.cancel()
    }

    fun cancelBackgroundChange() {
        mBackgroundUri = null
        cancelTimer()
    }
}
