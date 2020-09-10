package ir.omidtaheri.uibase

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.LoadPoster(posterPath: String) {

    val requestOptions = RequestOptions()
    requestOptions.apply {
        placeholder(R.drawable.ic_baseline_local_movies_24)
    }

    GlideApp.with(this.context)
        .load(BuildConfig.POSTER_URL + posterPath)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(160, 160)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.LoadBackdrop(backdropPath: String) {
    val requestOptions = RequestOptions()
    requestOptions.apply {
        placeholder(R.drawable.ic_baseline_local_movies_24)
    }

    GlideApp.with(this.context)
        .load(BuildConfig.BACKDROP_URL + backdropPath)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(180, 180)
        .apply(requestOptions)
        .into(this)
}
