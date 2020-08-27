package ir.omidtaheri.search.ui.SearchFragment.adapters

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
import ir.omidtaheri.search.R
import ir.omidtaheri.search.databinding.LoadStateFooterViewItemBinding
import ir.omidtaheri.search.databinding.SearchListEmptyStateBinding
import ir.omidtaheri.search.databinding.SearchListItemBinding
import ir.omidtaheri.search.entity.MovieUiEntity

class SearchMovieAdapter(diffCallback: DiffUtil.ItemCallback<MovieUiEntity>) :
    PagingDataAdapter<MovieUiEntity, BaseViewHolder>(diffCallback) {

//    var items: MutableList<MovieUiEntity> = mutableListOf()
//
//    val VIEW_TYPE_EMPTY = 0
//    val VIEW_TYPE_NORMAL = 1

    lateinit var mCallback: Callback

    interface Callback {
        fun OnItemClick(MovieId: Int)
    }

    fun SetCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

//        when (viewType) {
//
//            VIEW_TYPE_EMPTY -> {
//                return EmptyViewHolder(
//                    SearchListEmptyStateBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                )
//            }
//
//            else ->
        return ViewHolder(
            SearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

//        }
//
    }

//    override fun getItemCount(): Int {
//        return if (items != null && items.size != 0) {
//            items.size
//        } else {
//            1
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (items != null && items.size > 0) {
//            VIEW_TYPE_NORMAL
//        } else {
//            VIEW_TYPE_EMPTY
//        }
//
//    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

//    //    Helpers
//    fun addItem(item: MovieUiEntity) {
//        items.add(item)
//        notifyItemInserted(items.size - 1)
//    }
//
//    fun addItems(list: List<MovieUiEntity>) {
//        items.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    fun remove(item: MovieUiEntity) {
//        val index = items.indexOf(item)
//        if (index >= 0) {
//            items.removeAt(index)
//            notifyItemRemoved(index)
//        }
//    }
//
//    fun clear() {
//        items.clear()
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(val binding: SearchListItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val FavoriteUiEntity = getItem(position)

            binding.apply {
                //  glide // movieImageView.setImageResource(MovieUiEntity.poster_path)
                titleMovie.text = FavoriteUiEntity!!.title
                root.setOnClickListener {
                    mCallback.OnItemClick(FavoriteUiEntity.id)
                }
            }
        }
    }

    inner class EmptyViewHolder(val binding: SearchListEmptyStateBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {

            binding.apply {
            }
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
