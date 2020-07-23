package ir.omidtaheri.mainpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.interactor.GetMovies
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper

class MainViewModel (GetMoviesUseCase: GetMovies,UiDomainMapper : MovieEntityUiDomainMapper): BaseViewModel() {
    // TODO: Implement the ViewModel
}