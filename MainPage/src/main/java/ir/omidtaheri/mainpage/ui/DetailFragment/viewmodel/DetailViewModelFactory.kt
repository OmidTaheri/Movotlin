package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.FavorieMovie
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesSinglePage
import ir.omidtaheri.domain.interactor.UnfavoriteMovie
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieVideoEntityUiDomainMapper
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    val getDetailMovieUseCase: GetMovieDetail,
    val getMovieImagesById: GetMovieImagesById,
    val getMovieVideosById: GetMovieVideosById,
    val getSimilarMovies: GetSimilarMoviesSinglePage,
    val favorieMovie: FavorieMovie,
    val unfavoriteMovie: UnfavoriteMovie,
    val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(
            getDetailMovieUseCase,
            getMovieImagesById,
            getMovieVideosById,
            getSimilarMovies,
            favorieMovie,
            unfavoriteMovie,
            movieDetailEntityUiDomainMapper,
            movieImageEntityUiDomainMapper,
            movieVideoEntityUiDomainMapper,
            movieEntityUiDomainMapper,
            application
        ) as T
    }
}
