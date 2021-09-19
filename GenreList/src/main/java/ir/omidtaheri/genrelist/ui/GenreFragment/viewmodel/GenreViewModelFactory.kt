package ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetGenreList
import ir.omidtaheri.genrelist.mapper.GenreEntityUiDomainMapper
import javax.inject.Inject

class GenreViewModelFactory @Inject constructor(
    val getGenreList: GetGenreList,
    val genreEntityUiDomainMapper: GenreEntityUiDomainMapper,
    val application: Application
) : ViewModelAssistedFactory<GenreViewModel> {
    override fun create(handle: SavedStateHandle): GenreViewModel {
        return GenreViewModel(
            getGenreList,
            genreEntityUiDomainMapper,
            handle,
            application
        )
    }

}
