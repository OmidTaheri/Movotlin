package ir.omidtaheri.detailpage.ui.DetailFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.detailpage.databinding.ImagesViewerEmptyStateBinding
import ir.omidtaheri.detailpage.databinding.ImagesViewerItemBinding
import ir.omidtaheri.detailpage.entity.Backdrop
import ir.omidtaheri.uibase.LoadBackdrop
import ir.omidtaheri.uibase.clear


class ImagesGalleryViewAdapter(val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    var items: MutableList<Backdrop> = mutableListOf()

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_NORMAL = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        when (viewType) {

            VIEW_TYPE_EMPTY -> {
                return EmptyViewHolder(
                    ImagesViewerEmptyStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else ->
                return ViewHolder(
                    ImagesViewerItemBinding.inflate(
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

    //    Helpers
    fun addItem(item: Backdrop) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<Backdrop>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(item: Backdrop) {
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

    inner class ViewHolder(val binding: ImagesViewerItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val movieBackdropEntity = items.get(position)

            binding.apply {
                movieImageView.LoadBackdrop(movieBackdropEntity.filePath, context)
            }
        }
    }

    inner class EmptyViewHolder(val binding: ImagesViewerEmptyStateBinding) :
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
