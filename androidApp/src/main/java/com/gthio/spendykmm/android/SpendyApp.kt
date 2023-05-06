package com.gthio.spendykmm.android

import android.app.Application
import com.gthio.spendykmm.android.di.vmModule
import com.gthio.spendykmm.di.repositoryModule
import com.gthio.spendykmm.di.storeModule
import com.gthio.spendykmm.di.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpendyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpendyApp)
            modules(
                utilModule,
                repositoryModule,
                storeModule,
                vmModule,
            )
        }
    }
}