package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.remote.dto.response.GetMovieDetailResponse

class MovieDetailResponseMapper :ResponseDtoMapper<GetMovieDetailResponse, MovieDetailDataEntity> {
    override fun mapFromDTO(from: GetMovieDetailResponse): MovieDetailDataEntity {
        TODO("Not yet implemented")
    }
}