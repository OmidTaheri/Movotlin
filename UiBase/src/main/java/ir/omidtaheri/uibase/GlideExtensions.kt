package ir.omidtaheri.uibase

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions

fun ImageView.LoadPoster(posterPath: String) {

    val requestOptions = RequestOptions()
    requestOptions.apply {
        placeholder(R.drawable.ic_baseline_local_movies_24)
    }

    GlideApp.with(this.context)
        .load(BuildConfig.POSTER_URL + posterPath)
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
        .apply(requestOptions)
        .into(this)
}
