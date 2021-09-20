package ir.omidtaheri.daggercore.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.local.datasource.MovieLocalDataSourceImp
import ir.omidtaheri.movotlin.di.modules.LocalModule
import javax.inject.Singleton

@Module(includes = [LocalModule::class])
interface LocalDataSourceModule {

    @Singleton
    @Binds
     fun provideMovieLocalDataSource(
        datasource: MovieLocalDataSourceImp
    ): MovieLocalDataSourceInterface
}
