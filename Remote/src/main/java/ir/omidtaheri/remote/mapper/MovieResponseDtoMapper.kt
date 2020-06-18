package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.remote.dto.response.GetMoviesResponse

class MovieResponseDtoMapper : ResponseDtoMapper<GetMoviesResponse, List<MovieDataEntity>> {

    override fun mapFromDTO(from: GetMoviesResponse): List<MovieDataEntity> {
        TODO("Not yet implemented")
    }


}