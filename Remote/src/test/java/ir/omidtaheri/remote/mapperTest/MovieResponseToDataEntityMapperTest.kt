package ir.omidtaheri.remote.mapperTest

import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.remote.entity.response.MultiMovieResponseRemoteEntity
import ir.omidtaheri.remote.fackEntity.FackFacktory
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieResponseToDataEntityMapperTest {

    lateinit var mapper: MovieResponseToDataEntityMapper

    @Before
    fun setUp() {
        mapper = MovieResponseToDataEntityMapper()
    }

    @Test
    fun mapFromDTO() {
        val input_entity = FackFacktory.Fack_multiMovieResponseRemoteEntity
        val output_entity = mapper.mapFromDTO(input_entity)

        assertMultiMovieResponseeEntity(input_entity, output_entity)
    }

    private fun assertMultiMovieResponseeEntity(
        inputEntity: MultiMovieResponseRemoteEntity,
        outputEntity: MultiMovieDataEntity
    ) {
        assertEquals(inputEntity.results[0].overview, outputEntity.results[0].overview)
        assertEquals(inputEntity.results[0].title, outputEntity.results[0].title)
    }
}
