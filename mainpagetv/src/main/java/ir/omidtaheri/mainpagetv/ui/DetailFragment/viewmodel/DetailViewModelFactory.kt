package ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesWithoutPaging
import ir.omidtaheri.mainpagetv.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MultiMovieEntityUiDomainMapper
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    val getDetailMovieUseCase: GetMovieDetail,
    val getMovieImagesById: GetMovieImagesById,
    val getMovieVideosById: GetMovieVideosById,
    val getSimilarMovies: GetSimilarMoviesWithoutPaging,
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
            movieDetailEntityUiDomainMapper,
            movieImageEntityUiDomainMapper,
            movieVideoEntityUiDomainMapper,
            multiMovieEntityUiDomainMapper,
            application
        ) as T
    }
}
