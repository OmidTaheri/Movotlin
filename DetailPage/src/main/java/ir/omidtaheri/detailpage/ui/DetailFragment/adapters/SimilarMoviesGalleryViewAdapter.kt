package ir.omidtaheri.detailpage.ui.DetailFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.detailpage.databinding.MovieViewerEmptyStateBinding
import ir.omidtaheri.detailpage.databinding.MovieViewerItemBinding
import ir.omidtaheri.detailpage.entity.MovieUiEntity
import ir.omidtaheri.uibase.LoadPoster
import ir.omidtaheri.uibase.clear
import kotlinx.android.synthetic.main.movie_viewer_item.view.*

class SimilarMoviesGalleryViewAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieUiEntity>,
    val context: Context
) :
    PagingDataAdapter<MovieUiEntity, BaseViewHolder>(diffCallback) {

    lateinit var mCallback: Callback

    interface Callback {
        fun onItemClick(movieId: Int)
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {


        return ViewHolder(
            MovieViewerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ViewHolder(val binding: MovieViewerItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val movieUiEntity = getItem(position)

            binding.apply {
                movieUiEntity?.posterPath?.let { movieImageView.LoadPoster(it, context) }
                titleMovie.text = movieUiEntity!!.title
                root.setOnClickListener {
                    mCallback.onItemClick(movieUiEntity.id)
                }
            }
        }
    }

    inner class EmptyViewHolder(val binding: MovieViewerEmptyStateBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {

            binding.apply {
            }
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        if (holder is ViewHolder) {
            holder.itemView.movieImageView.clear(context)
        }
    }

}

object MovieUiEntityComparator : DiffUtil.ItemCallback<MovieUiEntity>() {
    override fun areItemsTheSame(oldItem: MovieUiEntity, newItem: MovieUiEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiEntity, newItem: MovieUiEntity): Boolean {
        return oldItem == newItem
    }
}
