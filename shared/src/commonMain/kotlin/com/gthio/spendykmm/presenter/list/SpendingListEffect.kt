package com.gthio.spendykmm.presenter.list

import com.gthio.spendykmm.domain.KmmUUID

sealed interface SpendingListEffect {
    data class NavigateToEdit(val id: KmmUUID) : SpendingListEffect
    object NavigateToAdd : SpendingListEffect
}