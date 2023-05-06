package com.gthio.spendykmm.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface SpendingRepository {
    fun observeSpendings(start: Instant, end: Instant): Flow<List<Spending>>
    suspend fun addSpending(spending: Spending)
    suspend fun updateSpending(spending: Spending)
    suspend fun deleteSpending(spending: Spending)
    suspend fun getSpending(id: KmmUUID): Spending?
}