package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.ReviewDataEntity
import ir.omidtaheri.remote.dto.response.GetReviewResponse

class MovieReviewResponseMapper : ResponseDtoMapper<GetReviewResponse, List<ReviewDataEntity>> {
    override fun mapFromDTO(from: GetReviewResponse): List<ReviewDataEntity> {
        TODO("Not yet implemented")
    }
}