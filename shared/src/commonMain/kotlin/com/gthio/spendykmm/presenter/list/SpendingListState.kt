package com.gthio.spendykmm.presenter.list

import com.gthio.spendykmm.domain.Spending

data class SpendingListState(
    val spendings: List<Spending> = emptyList(),
    val spendingMarkedForDeletion: List<Spending> = emptyList(),
    val isMarkActive: Boolean = false,
    val isLoading: Boolean = false,
)