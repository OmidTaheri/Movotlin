package ir.omidtaheri.viewcomponents.MultiStatePage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.viewcomponents.databinding.MultiStatePageBinding
import kotlinx.android.synthetic.main.multi_state_empty_state.view.*
import kotlinx.android.synthetic.main.multi_state_error_state.view.*


class MultiStatePage(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val viewBinding: MultiStatePageBinding =
        MultiStatePageBinding.inflate(LayoutInflater.from(context), this, true)

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

    private fun emptyLayoutVisibility(show: Boolean) {
        if (show) {
            viewBinding.root.empty_layout.visibility = View.VISIBLE
        } else {
            viewBinding.root.empty_layout.visibility = View.GONE
        }
    }

    fun setErrorText(text: String) {
        viewBinding.root.error_text.text = text
    }

    fun setErrorButtonText(text: String) {
        viewBinding.root.error_btn_retry.text = text
    }

    fun setEmptyText(text: String) {
        viewBinding.root.messageEmpty.text = text
    }

    fun setEmptyImage(resId: Int) {
        viewBinding.root.imageViewEmpty.setImageResource(resId)
    }

    fun errorButtonClickListner(listner: OnClickListener) {
        viewBinding.root.error_btn_retry.setOnClickListener(listner)
    }


    fun toLoadingState() {
        recyclerViewVisibility(false)
        progressBarVisibility(true)
        errorLayoutVisibility(false)
        emptyLayoutVisibility(false)
    }

    fun toErrorState(listner: OnClickListener) {
        recyclerViewVisibility(false)
        progressBarVisibility(false)
        errorLayoutVisibility(true)
        emptyLayoutVisibility(false)
        errorButtonClickListner(listner)
    }

    fun toEmptyState() {
        recyclerViewVisibility(false)
        progressBarVisibility(false)
        errorLayoutVisibility(false)
        emptyLayoutVisibility(true)
    }

    fun toDateState() {
        recyclerViewVisibility(true)
        progressBarVisibility(false)
        errorLayoutVisibility(false)
        emptyLayoutVisibility(false)
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
