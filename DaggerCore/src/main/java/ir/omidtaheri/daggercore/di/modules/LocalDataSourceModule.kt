package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.local.datasource.MovieLocalDataSourceImp
import ir.omidtaheri.movotlin.di.modules.LocalModule

@Module(includes = [ LocalModule::class])
class LocalDataSourceModule {

    @Provides
    fun provideMovieLocalDataSource(
        datasource: MovieLocalDataSourceImp
    ): MovieLocalDataSourceInterface {
        return datasource
    }
}
