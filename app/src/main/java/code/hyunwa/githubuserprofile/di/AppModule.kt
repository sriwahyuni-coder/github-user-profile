package code.hyunwa.githubuserprofile.di

import code.hyunwa.githubuserprofile.data.source.remote.RemoteDataSource
import code.hyunwa.githubuserprofile.data.source.remote.network.ApiConfig
import org.koin.dsl.module

val appModule = module {
    single { ApiConfig.provideApiService }
    single { RemoteDataSource(get()) }

}