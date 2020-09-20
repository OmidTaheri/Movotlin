package ir.omidtaheri.uibase

import android.content.SharedPreferences
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


fun saveBundle(editor: SharedPreferences.Editor, key: String, bundle: Bundle?) {
   val type =object: TypeToken<Bundle>(){}.type

    val gson: Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    bundle?.let {
        val json = gson.toJson(bundle, type)
        editor.putString(key, json)
    } ?: editor.putString(key, "")
}

fun loadBundle(sharedPreferences: SharedPreferences, key: String): Bundle? {
    val type =object: TypeToken<Bundle>(){}.type

    val gson: Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    val data = sharedPreferences.getString(key, "")
    val bundleObject = gson.fromJson<Bundle>(data, type)
    return bundleObject
}



fun saveRecyclerViewStat(
    editor: SharedPreferences.Editor,
    key: String,
    parcelable: LinearLayoutManager.SavedState?
) {
    val type =object: TypeToken<LinearLayoutManager.SavedState>(){}.type

    parcelable?.let {
        val json = Gson().toJson(parcelable, type)
        editor.putString(key, json)
    } ?: editor.putString(key, "")
}

fun loadRecyclerViewState(sharedPreferences: SharedPreferences, key: String): LinearLayoutManager.SavedState? {
    val type =object: TypeToken<LinearLayoutManager.SavedState>(){}.type

    val data = sharedPreferences.getString(key, "")
    val parcelableObject = Gson().fromJson<LinearLayoutManager.SavedState>(data, type)
    return parcelableObject
}





