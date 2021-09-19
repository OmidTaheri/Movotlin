package ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetGenreList
import ir.omidtaheri.genrelist.mapper.GenreEntityUiDomainMapper
import javax.inject.Inject

class GenreViewModelFactory @Inject constructor(
    private val getGenreList: GetGenreList,
    private val genreEntityUiDomainMapper: GenreEntityUiDomainMapper,
    private val application: Application
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
