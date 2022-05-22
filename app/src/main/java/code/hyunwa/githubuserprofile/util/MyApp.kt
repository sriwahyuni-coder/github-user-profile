package code.hyunwa.githubuserprofile.util

import android.app.Application
import code.hyunwa.githubuserprofile.di.appModule
import code.hyunwa.githubuserprofile.di.repositoryModule
import code.hyunwa.githubuserprofile.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }
}