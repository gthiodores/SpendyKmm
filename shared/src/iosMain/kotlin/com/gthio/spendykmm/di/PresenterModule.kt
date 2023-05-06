package com.gthio.spendykmm.di

import com.gthio.spendykmm.ui.SpendingListPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory { SpendingListPresenter(get()) }
}