package ir.omidtaheri.mainpage.ui.MovieFullList.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.LoadStateFooterViewItemBinding
import ir.omidtaheri.mainpage.databinding.MovieFullListItemBinding
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.uibase.LoadBackdrop
import ir.omidtaheri.uibase.LoadPoster
import ir.omidtaheri.uibase.clear
import kotlinx.android.synthetic.main.movie_viewer_item.view.*

class MovieFullListAdapter(diffCallback: DiffUtil.ItemCallback<MovieUiEntity>,val context: Context) :
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
            MovieFullListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ViewHolder(val binding: MovieFullListItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val movieUiEntity = getItem(position)

            binding.apply {
                movieUiEntity?.posterPath?.let { movieImageView.LoadPoster(it,context) }
                    ?: movieUiEntity?.backdropPath?.let { movieImageView.LoadBackdrop(it,context) }
                titleMovie.text = movieUiEntity?.title
                root.setOnClickListener {
                    mCallback.onItemClick(movieUiEntity!!.id)
                }
            }
        }
    }


    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        if (holder is  ViewHolder) {
            holder.binding.movieImageView.clear(context)
        }
    }
}

object MovieUiEntityComparator : DiffUtil.ItemCallback<MovieUiEntity>() {
    override fun areItemsTheSame(oldItem: MovieUiEntity, newItem: MovieUiEntity): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiEntity, newItem: MovieUiEntity): Boolean {
        return oldItem == newItem
    }
}

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.load_state_footer_view_item, parent, false)
) {
    private val binding = LoadStateFooterViewItemBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar
    private val errorMsg: TextView = binding.errorMsg
    private val retry: Button = binding.retryButton
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        progressBar.visibility =
            if (loadState is LoadState.Loading) View.VISIBLE else View.INVISIBLE
        retry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.INVISIBLE
        errorMsg.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.INVISIBLE
    }
}

class FooterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent, retry)
    }
}
