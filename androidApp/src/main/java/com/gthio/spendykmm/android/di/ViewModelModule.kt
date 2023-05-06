package com.gthio.spendykmm.android.di

import com.gthio.spendykmm.android.list.SpendingListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { SpendingListViewModel(get()) }
}