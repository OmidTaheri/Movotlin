package ir.omidtaheri.detailpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.detailpage.mapper.FavoritedMovieEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.domain.interactor.FavorieMovie
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesSinglePage
import ir.omidtaheri.domain.interactor.UnfavoriteMovie
import ir.omidtaheri.domain.interactor.base.Schedulers
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    private val getDetailMovieUseCase: GetMovieDetail,
    private val getMovieImagesById: GetMovieImagesById,
    private val getMovieVideosById: GetMovieVideosById,
    private val getSimilarMovies: GetSimilarMoviesSinglePage,
    private val favorieMovie: FavorieMovie,
    private val unfavoriteMovie: UnfavoriteMovie,
    private val getFavoriedMovieList: GetFavoriedMovieList,
    private val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    private val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    private val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    private val schedulers: Schedulers,
    private val application: Application
) : ViewModelAssistedFactory<DetailViewModel> {

    override fun create(handle: SavedStateHandle): DetailViewModel {
        return DetailViewModel(
            getDetailMovieUseCase,
            getMovieImagesById,
            getMovieVideosById,
            getSimilarMovies,
            favorieMovie,
            unfavoriteMovie,
            getFavoriedMovieList,
            movieDetailEntityUiDomainMapper,
            movieImageEntityUiDomainMapper,
            movieVideoEntityUiDomainMapper,
            movieEntityUiDomainMapper,
            favoritedMovieEntityUiDomainMapper,
            schedulers,
            handle,
            application
        )
    }
}
