package ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetGenreList
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.mapper.GenreEntityUiDomainMapper
import ir.omidtaheri.genrelist.mapper.MultiMovieEntityUiDomainMapper

import javax.inject.Inject

class GenreViewModelFactory @Inject constructor(
    val getGenreList: GetGenreList,
    val genreEntityUiDomainMapper: GenreEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GenreViewModel(
            getGenreList,
            genreEntityUiDomainMapper,
            application
        ) as T
    }
}