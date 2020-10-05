package ir.omidtaheri.viewcomponents.GalleryViewer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.viewcomponents.databinding.GalleryPageBinding
import kotlinx.android.synthetic.main.multi_state_error_state.view.*

class GalleryViewer(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val viewbinding: GalleryPageBinding

    init {
        viewbinding = GalleryPageBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun recyclerViewVisibility(show: Boolean) {

        if (show) {
            viewbinding.recyclerView.visibility = View.VISIBLE
        } else {
            viewbinding.recyclerView.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(show: Boolean) {

        if (show) {
            viewbinding.progressBar.visibility = View.VISIBLE
        } else {
            viewbinding.progressBar.visibility = View.GONE
        }
    }

    private fun errorLayoutVisibility(show: Boolean) {

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

    fun ErrorButtonClickListner(listner: OnClickListener) {
        viewbinding.root.error_btn_retry.setOnClickListener(listner)
    }


    fun toLoadingState() {
        recyclerViewVisibility(false)
        progressBarVisibility(true)
        errorLayoutVisibility(false)
    }

    fun toErrorState(listner: OnClickListener) {
        recyclerViewVisibility(false)
        progressBarVisibility(false)
        errorLayoutVisibility(true)
        ErrorButtonClickListner(listner)
    }

    fun toDateState() {
        recyclerViewVisibility(true)
        progressBarVisibility(false)
        errorLayoutVisibility(false)
    }

    fun configRecyclerView(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        viewbinding.recyclerView.adapter = adapter
        viewbinding.recyclerView.layoutManager = layoutManager
    }


    fun getRecyclerView() = viewbinding.recyclerView

    fun setCustomLayoutAnimation(resId: Int) {

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(viewbinding.recyclerView.context, resId)
        getRecyclerView().setLayoutAnimation(animation)
    }
}
