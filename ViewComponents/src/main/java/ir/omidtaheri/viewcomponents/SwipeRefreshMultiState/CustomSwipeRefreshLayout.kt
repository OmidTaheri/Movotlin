package ir.omidtaheri.viewcomponents.SwipeRefreshMultiState

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CustomSwipeRefreshLayout(context: Context, attrs: AttributeSet?) :
    SwipeRefreshLayout(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}