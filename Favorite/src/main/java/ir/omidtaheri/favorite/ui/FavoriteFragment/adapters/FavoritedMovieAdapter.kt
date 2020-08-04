package ir.omidtaheri.favorite.ui.FavoriteFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.favorite.databinding.FavoriteListEmptyStateBinding
import ir.omidtaheri.favorite.databinding.FavoriteListItemBinding
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity


class FavoritedMovieAdapter : RecyclerView.Adapter<BaseViewHolder>() {


    var items: MutableList<FavoritedMovieUiEntity> = mutableListOf()

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_NORMAL = 1

    lateinit var mCallback: Callback

    interface Callback {
        fun OnItemClick(MovieId: Int)
    }

    fun SetCallback(callback: Callback) {
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
        return if (items != null && items.size != 0) {
            items.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items != null && items.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }

    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }


    //    Helpers
    fun addItem(item: FavoritedMovieUiEntity) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<FavoritedMovieUiEntity>) {
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
            val FavoriteUiEntity = items.get(position)

            binding.apply {
                //  glide // movieImageView.setImageResource(MovieUiEntity.poster_path)
                titleMovie.text = FavoriteUiEntity.title
                root.setOnClickListener {
                    mCallback.OnItemClick(FavoriteUiEntity.id)
                }
            }


        }
    }


    inner class EmptyViewHolder(val binding: FavoriteListEmptyStateBinding) :
        BaseViewHolder(binding.root) {


        override fun onBind(position: Int) {

            binding.apply {

            }
        }
    }
}

