package ir.omidtaheri.remote.datasource


import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieReviewsRemoteDataSourceInterface
import ir.omidtaheri.data.entity.ReviewDataEntity
import ir.omidtaheri.remote.mapper.MovieReviewResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieReviewApi
import javax.inject.Inject

class MovieReviewsRemoteDataSourceImp  @Inject constructor(
    val movieReviewApi: MovieReviewApi,
    val movieReviewResponseToDataEntityMapper: MovieReviewResponseToDataEntityMapper
) : MovieReviewsRemoteDataSourceInterface {
    override fun GetReviews(MovieId: Long): Single<List<ReviewDataEntity>> {
        return movieReviewApi.getMovieReviews(MovieId).map {
            movieReviewResponseToDataEntityMapper.mapFromDTO(it)
        }
    }
}