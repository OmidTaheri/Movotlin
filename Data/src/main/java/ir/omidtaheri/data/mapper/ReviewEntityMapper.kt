package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.ReviewDataEntity
import ir.omidtaheri.domain.entity.ReviewEntity

class ReviewEntityMapper : Mapper<ReviewDataEntity, ReviewEntity> {
    override fun mapFromDataEntity(from: ReviewDataEntity): ReviewEntity {
        return ReviewEntity(from.id, from.author, from.content, from.url)
    }

    override fun mapToDataEntity(from: ReviewEntity): ReviewDataEntity {
        return ReviewDataEntity(from.id, from.author, from.content, from.url)
    }
}