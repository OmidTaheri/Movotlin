package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.ReviewDomainEntity
import ir.omidtaheri.mainpage.entity.ReviewUiEntity
import javax.inject.Inject

class ReviewEntityUiDomainMapper @Inject constructor(): Ui_Domain_Mapper<ReviewUiEntity, ReviewDomainEntity> {
    override fun mapFromUiEntity(from: ReviewUiEntity): ReviewDomainEntity {
        return ReviewDomainEntity(from.id, from.author, from.content, from.url)
    }

    override fun mapToUiEntity(from: ReviewDomainEntity): ReviewUiEntity {
        return ReviewUiEntity(from.id, from.author, from.content, from.url)
    }
}