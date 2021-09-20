package ir.omidtaheri.favorite.ui.FavoriteFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.favorite.databinding.FavoriteListEmptyStateBinding
import ir.omidtaheri.favorite.databinding.FavoriteListItemBinding
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity
import ir.omidtaheri.uibase.LoadBackdrop
import ir.omidtaheri.uibase.LoadPoster
import ir.omidtaheri.uibase.clear

class FavoritedMovieAdapter(private val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private var items: MutableList<FavoritedMovieUiEntity> = mutableListOf()
    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_NORMAL = 1
    private lateinit var mCallback: Callback

    interface Callback {
        fun onItemClick(movieId: Int)
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        when (viewType) {

            VIEW_TYPE_EMPTY -> {
                return EmptyViewHolder(
                    FavoriteListEmptyStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else ->
                return ViewHolder(
                    FavoriteListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    override fun getItemCount(): Int {
        return if (items.size != 0) {
            items.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    //Helpers

    fun addItem(item: FavoritedMovieUiEntity) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<FavoritedMovieUiEntity>) {
        clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(item: FavoritedMovieUiEntity) {
        val index = items.indexOf(item)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FavoriteListItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val favoriteUiEntity = items[position]

            binding.apply {
                favoriteUiEntity.posterPath?.let { movieImageView.LoadPoster(it, context) }
                titleMovie.text = favoriteUiEntity.title
                root.setOnClickListener {
                    mCallback.onItemClick(favoriteUiEntity.id)
                }
            }
        }
    }

    inner class EmptyViewHolder(private val binding: FavoriteListEmptyStateBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {

            binding.apply {
            }
        }
    }


    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        if (holder is ViewHolder) {
            holder.binding.movieImageView.clear(context)
        }
    }


}
