package ir.omidtaheri.mainpage.ui.DetailFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.mainpage.databinding.MovieViewerEmptyStateBinding
import ir.omidtaheri.mainpage.databinding.MovieViewerItemBinding
import ir.omidtaheri.mainpage.entity.MovieUiEntity


class SimilarMoviesGalleryViewAdapter : RecyclerView.Adapter<BaseViewHolder>() {


    var items: MutableList<MovieUiEntity> = mutableListOf()

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
                    MovieViewerEmptyStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else ->
                return ViewHolder(
                    MovieViewerItemBinding.inflate(
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
    fun addItem(item: MovieUiEntity) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<MovieUiEntity>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(item: MovieUiEntity) {
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


    inner class ViewHolder(val binding: MovieViewerItemBinding) : BaseViewHolder(binding.root) {


        override fun onBind(position: Int) {
            val MovieUiEntity = items.get(position)

            binding.apply {
                //  glide // movieImageView.setImageResource(MovieUiEntity.poster_path)
                titleMovie.text = MovieUiEntity.title
                root.setOnClickListener {
                    mCallback.OnItemClick(MovieUiEntity.id)
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
}


