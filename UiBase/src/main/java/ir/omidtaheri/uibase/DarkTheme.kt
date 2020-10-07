package ir.omidtaheri.uibase

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate


const val DARK_MODE = "DARK_MODE"


fun getDarkModeStatus(ctx: Context): Boolean {
    var isNightModeEnabled = false
    val mPrefs: SharedPreferences =
        ctx.getSharedPreferences("ApplicationSetting", MODE_PRIVATE)
    isNightModeEnabled = mPrefs.getBoolean(DARK_MODE, false)
    return isNightModeEnabled
}

fun enableDarkMode(darkMode: Boolean) {

    if (darkMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}

fun switchThemeMode(ctx: Context) {
    var darkMode = false

    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        darkMode = true

    if (darkMode) {
        setDarkModeStatus(ctx, !darkMode)
    } else {
        setDarkModeStatus(ctx, !darkMode)
    }
}


fun setDarkModeStatus(ctx: Context, isNightModeEnabled: Boolean) {
    val mPrefs: SharedPreferences =
        ctx.getSharedPreferences("ApplicationSetting", MODE_PRIVATE)
    val ed: SharedPreferences.Editor = mPrefs.edit()
    ed.putBoolean(DARK_MODE, isNightModeEnabled)
    ed.apply()
    enableDarkMode(isNightModeEnabled)
}




