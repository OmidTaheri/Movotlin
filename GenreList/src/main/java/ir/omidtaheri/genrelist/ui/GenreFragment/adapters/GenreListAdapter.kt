package ir.omidtaheri.genrelist.ui.GenreFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.genrelist.databinding.GenreListItemBinding
import ir.omidtaheri.genrelist.entity.GenreUiEntity
import ir.omidtaheri.uibase.clear

class GenreListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var items: MutableList<GenreUiEntity> = mutableListOf()

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_NORMAL = 1

    lateinit var mCallback: Callback

    interface Callback {
        fun onItemClick(genreId: Int)
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        when (viewType) {

            VIEW_TYPE_EMPTY -> {
                return EmptyViewHolder(
                    GenreListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else ->
                return ViewHolder(
                    GenreListItemBinding.inflate(
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
    fun addItem(item: GenreUiEntity) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<GenreUiEntity>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(item: GenreUiEntity) {
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

    inner class ViewHolder(val binding: GenreListItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val genreUiEntity = items.get(position)

            binding.apply {
                genreName.text = genreUiEntity.name
                root.setOnClickListener {
                    mCallback.onItemClick(genreUiEntity.id)
                }
            }
        }
    }

    inner class EmptyViewHolder(val binding: GenreListItemBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {

            binding.apply {
            }
        }
    }



}
