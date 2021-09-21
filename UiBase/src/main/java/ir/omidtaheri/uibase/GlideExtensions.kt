package ir.omidtaheri.uibase

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.LoadPoster(posterPath: String, myConetxt: Context) {

    val requestOptions = RequestOptions()

    GlideApp.with(myConetxt)
        .load(BuildConfig.POSTER_URL + posterPath)
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .override(160, 160)
        .thumbnail(0.4f)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.LoadBackdrop(backdropPath: String, myConetxt: Context) {
    val requestOptions = RequestOptions()

    GlideApp.with(myConetxt)
        .load(BuildConfig.BACKDROP_URL + backdropPath)
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .override(180, 180)
        .thumbnail(0.6f)
        .apply(requestOptions)
        .into(this)
}


fun ImageView.LoadMainBackdrop(backdropPath: String, myConetxt: Context) {
    val requestOptions = RequestOptions()

    GlideApp.with(myConetxt)
        .load(BuildConfig.BACKDROP_URL + backdropPath)
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .override(300, 300)
        .thumbnail(0.6f)
        .apply(requestOptions)
        .into(this)
}


fun ImageView.clear(myConetxt: Context) {
    GlideApp.with(myConetxt)
        .clear(this)
}


fun Fragment.onDestroyGlide() {
    GlideApp.get(requireContext()).clearMemory()
}
