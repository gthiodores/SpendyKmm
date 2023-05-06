package com.gthio.spendykmm.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.gthio.spendykmm.presenter.list.SpendingListStore
import com.gthio.spendykmm.presenter.list.SpendingListStoreFactory
import org.koin.dsl.module

val storeModule = module {
    factory<StoreFactory> { DefaultStoreFactory() }
    factory { SpendingListStoreFactory(storeFactory = get(), repository = get()).create() }
}