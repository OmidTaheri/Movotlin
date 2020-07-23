package ir.omidtaheri.viewcomponents.MultiStatePage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.viewcomponents.databinding.MultiStatePageBinding
import kotlinx.android.synthetic.main.multi_state_empty_state.view.*
import kotlinx.android.synthetic.main.multi_state_error_state.view.*

class MultiStatePage(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val viewbinding: MultiStatePageBinding

    init {
        viewbinding = MultiStatePageBinding.inflate(LayoutInflater.from(context), this, true)
    }


    private fun RecyclerViewVisibility(show: Boolean) {

        if (show) {
            viewbinding.recyclerView.visibility = View.VISIBLE
        } else {
            viewbinding.recyclerView.visibility = View.GONE

        }
    }


    private fun ProgressBarVisibility(show: Boolean) {

        if (show) {
            viewbinding.progressBar.visibility = View.VISIBLE
        } else {
            viewbinding.progressBar.visibility = View.GONE

        }
    }

    private fun ErrorLayoutVisibility(show: Boolean) {

        if (show) {
            viewbinding.root.error_layout.visibility = View.VISIBLE
        } else {
            viewbinding.root.error_layout.visibility = View.GONE

        }
    }

    private fun EmptyLayoutVisibility(show: Boolean) {
        if (show) {
            viewbinding.root.empty_layout.visibility = View.VISIBLE
        } else {
            viewbinding.root.empty_layout.visibility = View.GONE
        }

    }


    fun setErrorText(text: String) {
        viewbinding.root.error_text.text = text
    }

    fun setErrorButtonText(text: String) {
        viewbinding.root.error_btn_retry.text = text
    }

    fun setEmptyText(text: String) {
        viewbinding.root.messageEmpty.text = text
    }

    fun setEmptyImage(ResId: Int) {
        viewbinding.root.imageViewEmpty.setImageResource(ResId)
    }

    fun ToLoadingState() {
        RecyclerViewVisibility(false)
        ProgressBarVisibility(true)
        ErrorLayoutVisibility(false)
        EmptyLayoutVisibility(false)

    }

    fun ToErrorState() {
        RecyclerViewVisibility(false)
        ProgressBarVisibility(false)
        ErrorLayoutVisibility(true)
        EmptyLayoutVisibility(false)
    }

    fun ToEmptyState() {
        RecyclerViewVisibility(false)
        ProgressBarVisibility(false)
        ErrorLayoutVisibility(false)
        EmptyLayoutVisibility(true)
    }


    fun ToDateState() {
        RecyclerViewVisibility(true)
        ProgressBarVisibility(false)
        ErrorLayoutVisibility(false)
        EmptyLayoutVisibility(false)
    }


    fun ConfigRecyclerView(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        viewbinding.recyclerView.adapter = adapter
        viewbinding.recyclerView.layoutManager = layoutManager
    }

}