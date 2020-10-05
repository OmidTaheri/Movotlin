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

    private val viewbinding: SwipeRefreshMultiStatePageBinding

    init {
        viewbinding =
            SwipeRefreshMultiStatePageBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun swipeRefreshLayoutVisibility(show: Boolean) {

        if (show) {
            viewbinding.swiperefresh.visibility = View.VISIBLE
        } else {
            viewbinding.swiperefresh.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(show: Boolean) {

        if (show) {

            if (!viewbinding.swiperefresh.isRefreshing) {
                viewbinding.swiperefresh.isRefreshing = show
            }

        } else {
            viewbinding.swiperefresh.isRefreshing = show
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

    fun setEmptyText(text: String) {
        viewbinding.root.messageEmpty.text = text
    }

    fun setEmptyImage(resId: Int) {
        viewbinding.root.imageViewEmpty.setImageResource(resId)
    }

    fun ErrorButtonClickListner(listner: OnClickListener) {
        viewbinding.root.error_btn_retry.setOnClickListener(listner)
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
        ErrorButtonClickListner(listner)
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
        viewbinding.recyclerView.adapter = adapter
        viewbinding.recyclerView.layoutManager = layoutManager
    }

    fun getSwipeRefresh() = viewbinding.swiperefresh

    fun getRecyclerView() = viewbinding.recyclerView

    fun setCustomLayoutAnimation(resId: Int) {

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(viewbinding.recyclerView.context, resId)
        getRecyclerView().setLayoutAnimation(animation)
    }

}
