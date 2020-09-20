package ir.omidtaheri.mainpagetv.ui.MainFragment

import androidx.leanback.widget.ArrayObjectAdapter

data class MovieRow(val id: Int, val title: String, var page: Int, val adapter: ArrayObjectAdapter)
