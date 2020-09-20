package ir.omidtaheri.mainpagetv.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import ir.omidtaheri.mainpagetv.R
import ir.omidtaheri.mainpagetv.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpagetv.viewHolders.MovieDetailsViewHolder

class DetailsDescriptionPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.vh_movie_details, parent, false)
        return MovieDetailsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val movieDetailsViewHolder = viewHolder as MovieDetailsViewHolder
        val movieDetails = item as MovieDetailUiEntity
        movieDetailsViewHolder.bind(movieDetails)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}
