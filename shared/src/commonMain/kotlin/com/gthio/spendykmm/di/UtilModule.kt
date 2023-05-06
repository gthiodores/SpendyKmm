package com.gthio.spendykmm.di

import com.gthio.spendykmm.domain.DispatcherProvider
import org.koin.dsl.module

val utilModule = module {
    single { DispatcherProvider() }
}