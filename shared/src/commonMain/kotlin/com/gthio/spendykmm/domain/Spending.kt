package com.gthio.spendykmm.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Spending(
    val id: KmmUUID = KmmUUID(),
    val amount: Double = 0.0,
    val date: Instant = Clock.System.now(),
    val notes: String = "",
)