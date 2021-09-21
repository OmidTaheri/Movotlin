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
import kotlinx.android.synthetic.main.gallery_page.view.*
import kotlinx.android.synthetic.main.multi_state_error_state.view.*

class GalleryViewer(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val viewBinding: GalleryPageBinding =
        GalleryPageBinding.inflate(LayoutInflater.from(context), this)

    private fun recyclerViewVisibility(show: Boolean) {

        if (show) {
            viewBinding.recyclerView.visibility = View.VISIBLE
        } else {
            viewBinding.recyclerView.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(show: Boolean) {

        if (show) {
            viewBinding.progressBar.visibility = View.VISIBLE
        } else {
            viewBinding.progressBar.visibility = View.GONE
        }
    }

    private fun errorLayoutVisibility(show: Boolean) {

        if (show) {
            viewBinding.root.error_layout.visibility = View.VISIBLE
        } else {
            viewBinding.root.error_layout.visibility = View.GONE
        }
    }

    fun setErrorText(text: String) {
        viewBinding.errorLayout.errorText.text = text
    }

    fun setErrorButtonText(text: String) {
        viewBinding.errorLayout.errorBtnRetry.text = text
    }

    fun errorButtonClickListner(listner: OnClickListener) {
        viewBinding.errorLayout.errorBtnRetry.setOnClickListener(listner)
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
        errorButtonClickListner(listner)
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
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.layoutManager = layoutManager
    }


    fun getRecyclerView() = viewBinding.recyclerView

    fun setCustomLayoutAnimation(resId: Int) {

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(viewBinding.recyclerView.context, resId)
        getRecyclerView().layoutAnimation = animation
    }
}
