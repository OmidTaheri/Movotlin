package ir.omidtaheri.viewcomponents.SwipeRefreshMultiState

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.viewcomponents.databinding.SwipeRefreshMultiStatePageBinding
import kotlinx.android.synthetic.main.multi_state_empty_state.view.*
import kotlinx.android.synthetic.main.multi_state_error_state.view.*

class SwipeRefreshMultiStatePage(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val viewBinding: SwipeRefreshMultiStatePageBinding =
        SwipeRefreshMultiStatePageBinding.inflate(LayoutInflater.from(context), this, true)

    private fun swipeRefreshLayoutVisibility(show: Boolean) {

        if (show) {
            viewBinding.swiperefresh.visibility = View.VISIBLE
        } else {
            viewBinding.swiperefresh.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(show: Boolean) {

        if (show) {

            if (!viewBinding.swiperefresh.isRefreshing) {
                viewBinding.swiperefresh.isRefreshing = show
            }

        } else {
            viewBinding.swiperefresh.isRefreshing = show
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
        swipeRefreshLayoutVisibility(true)
        progressBarVisibility(true)
        errorLayoutVisibility(false)
    }

    fun toErrorState(listner: OnClickListener) {
        swipeRefreshLayoutVisibility(false)
        progressBarVisibility(false)
        errorLayoutVisibility(true)
        errorButtonClickListner(listner)
    }


    fun toDateState() {
        swipeRefreshLayoutVisibility(true)
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

    fun getSwipeRefresh() = viewBinding.swiperefresh

    fun getRecyclerView() = viewBinding.recyclerView

    fun setCustomLayoutAnimation(resId: Int) {

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(viewBinding.recyclerView.context, resId)
        getRecyclerView().layoutAnimation = animation
    }

}
