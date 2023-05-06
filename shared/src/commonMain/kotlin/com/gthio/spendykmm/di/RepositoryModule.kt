package com.gthio.spendykmm.di

import com.gthio.spendykmm.data.InMemorySpendingRepository
import com.gthio.spendykmm.domain.SpendingRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SpendingRepository> { InMemorySpendingRepository(get()) }
}