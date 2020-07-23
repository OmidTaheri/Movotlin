package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.ReviewDataEntity
import ir.omidtaheri.remote.entity.response.ReviewResponseRemoteEntity
import javax.inject.Inject

class MovieReviewResponseToDataEntityMapper @Inject constructor() :
    ResponseToData_EntityMapper<ReviewResponseRemoteEntity, List<ReviewDataEntity>> {
    override fun mapFromDTO(from: ReviewResponseRemoteEntity): List<ReviewDataEntity> {
        TODO("Not yet implemented")
    }
}