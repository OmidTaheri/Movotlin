package ir.omidtaheri.remote.datasource


import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieReviewsRemoteDataSourceInterface
import ir.omidtaheri.data.entity.ReviewDataEntity
import ir.omidtaheri.remote.mapper.MovieReviewResponseMapper
import ir.omidtaheri.remote.service.MovieReviewApi

class MovieReviewsRemoteDataSourceImp(
    val movieReviewApi: MovieReviewApi,
    val movieReviewResponseMapper: MovieReviewResponseMapper
) : MovieReviewsRemoteDataSourceInterface {
    override fun GetReviews(MovieId: Long): Single<List<ReviewDataEntity>> {
        return movieReviewApi.getMovieReviews(MovieId).map {
            movieReviewResponseMapper.mapFromDTO(it)
        }
    }
}