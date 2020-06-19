package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.ReviewDataEntity
import ir.omidtaheri.domain.entity.ReviewDomainEntity

class ReviewEntityDomainDataMapper : Domain_Data_Mapper<ReviewDataEntity, ReviewDomainEntity> {
    override fun mapFromDataEntity(from: ReviewDataEntity): ReviewDomainEntity {
        return ReviewDomainEntity(from.id, from.author, from.content, from.url)
    }

    override fun mapToDataEntity(from: ReviewDomainEntity): ReviewDataEntity {
        return ReviewDataEntity(from.id, from.author, from.content, from.url)
    }
}