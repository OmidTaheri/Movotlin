package ir.omidtaheri.androidbase

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    private val mActivityComponent: Any = TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Dagger Activity Component
    }


//    fun getActivityComponent(): ActivityComponent? {
//        return mActivityComponent
//    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String?>?,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun setUp()

    abstract fun showLoading()
    abstract fun hideLoading()

    abstract fun showStringError(message: String)
    abstract fun showResError(ResId: Int)


    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)


    abstract fun showDialog(message: String)


}