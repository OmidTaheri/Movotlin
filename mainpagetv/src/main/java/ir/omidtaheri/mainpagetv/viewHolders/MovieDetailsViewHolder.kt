package ir.omidtaheri.mainpagetv.viewHolders

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.leanback.widget.Presenter
import ir.omidtaheri.mainpagetv.R
import ir.omidtaheri.mainpagetv.entity.MovieDetailUiEntity

class MovieDetailsViewHolder(view: View?) : Presenter.ViewHolder(view) {

    lateinit var movieTitle: TextView
    lateinit var tagline: TextView
    lateinit var voteAverage: TextView
    lateinit var overview: TextView
    lateinit var genres: LinearLayout

    init {
        movieTitle = view?.findViewById(R.id.movie_title)!!
        tagline = view?.findViewById(R.id.tagline)
        voteAverage = view?.findViewById(R.id.vote)
        genres = view?.findViewById(R.id.genres)
        overview = view?.findViewById(R.id.overview)
    }

    fun bind(movieDetail: MovieDetailUiEntity) {
        voteAverage.setText(movieDetail.voteAverage.toString())
        tagline.setText(movieDetail.tagline)
        movieTitle.setText(movieDetail.title)
        overview.setText(movieDetail.overview)
        genres.removeAllViews()

        // Adds each genre to the genre layout
        for (genre in movieDetail.genres) {
            val tv = TextView(view.getContext())
            tv.setText(genre.name)
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadius = 4f
            shape.setColor(view.resources.getColor(R.color.colorPrimary))
            tv.setPadding(8, 8, 8, 8)
            tv.background = shape

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                params.setMargins(
                    0,
                    0,
                    16,
                    0
                )
                tv.layoutParams = params
            }

            genres.addView(tv)
        }
    }
}
