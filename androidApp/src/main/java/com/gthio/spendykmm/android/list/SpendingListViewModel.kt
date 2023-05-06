package com.gthio.spendykmm.android.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.gthio.spendykmm.domain.Spending
import com.gthio.spendykmm.presenter.list.SpendingListEffect
import com.gthio.spendykmm.presenter.list.SpendingListIntent
import com.gthio.spendykmm.presenter.list.SpendingListState
import com.gthio.spendykmm.presenter.list.SpendingListStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SpendingListViewModel(
    private val store: SpendingListStore
) : ViewModel() {
    private val _isDeleteConfirmationVisible = MutableStateFlow(false)
    val isDeleteConfirmationVisible = _isDeleteConfirmationVisible.asStateFlow()

    val uiState = store
        .states
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SpendingListState(),
        )
    val sideEffects: Flow<SpendingListEffect?> = store.labels

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
        _isDeleteConfirmationVisible.update { true }
    }

    fun onDeleteConfirmation(confirm: Boolean) {
        _isDeleteConfirmationVisible.update { false }

        if (confirm) store.accept(SpendingListIntent.DeleteMarkedSpendings)
    }
}