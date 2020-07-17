package ir.omidtaheri.viewcomponents.GalleryViewer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.viewcomponents.databinding.GalleryPageBinding
import kotlinx.android.synthetic.main.multi_state_error_state.view.*

class GalleryViewer(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val viewbinding: GalleryPageBinding

    init {
        viewbinding = GalleryPageBinding.inflate(LayoutInflater.from(context), this, true)
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


    fun setErrorText(text: String) {
        viewbinding.root.error_text.text = text
    }

    fun setErrorButtonText(text: String) {
        viewbinding.root.error_btn_retry.text = text
    }


    fun ToLoadingState() {
        RecyclerViewVisibility(false)
        ProgressBarVisibility(true)
        ErrorLayoutVisibility(false)


    }

    fun ToErrorState() {
        RecyclerViewVisibility(false)
        ProgressBarVisibility(false)
        ErrorLayoutVisibility(true)

    }


    fun ToDateState() {
        RecyclerViewVisibility(true)
        ProgressBarVisibility(false)
        ErrorLayoutVisibility(false)
    }


    fun ConfigRecyclerView(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        viewbinding.recyclerView.adapter = adapter
        viewbinding.recyclerView.layoutManager = layoutManager
    }

}