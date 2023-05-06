package com.gthio.spendykmm.presenter.list

import com.arkivanov.mvikotlin.core.store.Store

interface SpendingListStore : Store<SpendingListIntent, SpendingListState, SpendingListEffect>