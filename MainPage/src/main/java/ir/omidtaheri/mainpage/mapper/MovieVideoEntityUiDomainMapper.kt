package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.mainpage.entity.*
import javax.inject.Inject

class MovieVideoEntityUiDomainMapper @Inject constructor() :
    Ui_Domain_Mapper<MovieVideoUiEntity, MovieVideoDomainEntity> {


    override fun mapFromUiEntity(from: MovieVideoUiEntity): MovieVideoDomainEntity {
        return MovieVideoDomainEntity(
            from.id, from.results.map {
                ir.omidtaheri.domain.entity.Result(
                    it.id,
                    it.key,
                    it.name,
                    it.site,
                    it.size,
                    it.type
                )
            }
        )
    }

    override fun mapToUiEntity(from: MovieVideoDomainEntity): MovieVideoUiEntity {
        return MovieVideoUiEntity(
            from.id, from.results.map {
                Result(
                    it.id,
                    it.key,
                    it.name,
                    it.site,
                    it.size,
                    it.type
                )
            }
        )
    }


}