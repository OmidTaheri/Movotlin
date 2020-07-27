package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.*
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MultiMovieEntityUiDomainMapper
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    val getDetailMovieUseCase: GetMovieDetail,
    val getMovieImagesById: GetMovieImagesById,
    val getMovieVideosById: GetMovieVideosById,
    val getSimilarMovies: GetSimilarMovies,
    val favorieMovie: FavorieMovie,
    val unfavoriteMovie: UnfavoriteMovie,
    val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
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
            multiMovieEntityUiDomainMapper,
            application
        ) as T
    }
}