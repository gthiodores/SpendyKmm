package com.gthio.spendykmm.presenter.list

import com.gthio.spendykmm.domain.KmmUUID
import com.gthio.spendykmm.domain.Spending
import kotlinx.datetime.Instant

sealed interface SpendingListIntent {
    data class LoadSpendings(val start: Instant, val end: Instant) : SpendingListIntent
    data class SpendingSelected(val id: KmmUUID) : SpendingListIntent
    data class MarkSpendingToDelete(val spending: Spending) : SpendingListIntent
    data class UnmarkSpendingToDelete(val spending: Spending) : SpendingListIntent
    data class ToggleMarkDelete(val isActive: Boolean) : SpendingListIntent
    object DeleteMarkedSpendings : SpendingListIntent
    object AddSpending : SpendingListIntent
}