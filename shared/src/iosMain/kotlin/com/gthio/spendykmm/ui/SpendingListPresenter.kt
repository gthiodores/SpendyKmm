package com.gthio.spendykmm.ui

import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.gthio.spendykmm.domain.Spending
import com.gthio.spendykmm.presenter.list.SpendingListEffect
import com.gthio.spendykmm.presenter.list.SpendingListIntent
import com.gthio.spendykmm.presenter.list.SpendingListState
import com.gthio.spendykmm.presenter.list.SpendingListStore
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SpendingListPresenter(
    private val store: SpendingListStore,
) {
    private val _isDeleteConfirmationVisible = MutableStateFlow(false)

    @NativeCoroutinesState
    val isDeleteConfirmationVisible: StateFlow<Boolean> = _isDeleteConfirmationVisible.asStateFlow()

    @NativeCoroutines
    val uiState: Flow<SpendingListState> = store.states

    @NativeCoroutines
    val effects: Flow<SpendingListEffect?> = store.labels

    fun onAddTapped() {

        store.accept(SpendingListIntent.AddSpending)
    }

    fun onToggleDelete(isActive: Boolean) {
        store.accept(SpendingListIntent.ToggleMarkDelete(isActive))
    }

    fun onMarkForDeleteSpending(spending: Spending) {
        store.accept(SpendingListIntent.MarkSpendingToDelete(spending))
    }

    fun onRemoveMarkForDeleteSpending(spending: Spending) {
        store.accept(SpendingListIntent.UnmarkSpendingToDelete(spending))
    }

    fun onDelete() {
        store.accept(SpendingListIntent.DeleteMarkedSpendings)
    }

    fun onDeleteConfirmation(confirm: Boolean) {
        if (confirm) store.accept(SpendingListIntent.DeleteMarkedSpendings)
    }
}