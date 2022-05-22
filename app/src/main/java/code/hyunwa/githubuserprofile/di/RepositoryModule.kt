package code.hyunwa.githubuserprofile.di

import code.hyunwa.githubuserprofile.core.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get()) }
}