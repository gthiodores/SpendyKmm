package com.gthio.spendykmm.di

import com.gthio.spendykmm.ui.SpendingListPresenter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(utilModule, repositoryModule, storeModule, presenterModule)
    }
}

class SpendingListPresenterHelper : KoinComponent {
    private val presenter: SpendingListPresenter by inject()

    fun get(): SpendingListPresenter = presenter
}