package ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
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
    private val getDetailMovieUseCase: GetMovieDetail,
    private val getMovieImagesById: GetMovieImagesById,
    private val getMovieVideosById: GetMovieVideosById,
    private val getSimilarMovies: GetSimilarMoviesWithoutPaging,
    private val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    private val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    private val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    private val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<DetailViewModel> {
    override fun create(handle: SavedStateHandle): DetailViewModel {
        return DetailViewModel(
            getDetailMovieUseCase,
            getMovieImagesById,
            getMovieVideosById,
            getSimilarMovies,
            movieDetailEntityUiDomainMapper,
            movieImageEntityUiDomainMapper,
            movieVideoEntityUiDomainMapper,
            multiMovieEntityUiDomainMapper,
            handle,
            application
        )
    }

}
