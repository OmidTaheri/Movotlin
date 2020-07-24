package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovies
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    val GetDetailMovieUseCase: GetMovieDetail,
    val UiDomainMapper: MovieDetailEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(GetDetailMovieUseCase, UiDomainMapper, application) as T
    }
}