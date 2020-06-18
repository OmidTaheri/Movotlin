package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.CategoryDataEntity
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.domain.entity.CategoryEntity
import ir.omidtaheri.domain.entity.GenreEntity

class GenreEntityMapper : Mapper<GenreDataEntity, GenreEntity> {

    override fun mapFromDataEntity(from: GenreDataEntity): GenreEntity {

        return GenreEntity(from.id, from.name)
    }

    override fun mapToDataEntity(from: GenreEntity): GenreDataEntity {

        return GenreDataEntity(from.id, from.name)
    }
}