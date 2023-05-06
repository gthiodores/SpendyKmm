package com.gthio.spendykmm.data

import com.gthio.spendykmm.domain.DispatcherProvider
import com.gthio.spendykmm.domain.KmmUUID
import com.gthio.spendykmm.domain.Spending
import com.gthio.spendykmm.domain.SpendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class InMemorySpendingRepository(private val dispatchers: DispatcherProvider) : SpendingRepository {

    private val _mutableSpendings: MutableStateFlow<List<Spending>> = MutableStateFlow(
        listOf(
            Spending(amount = 1_200.0, date = Clock.System.now()),
            Spending(amount = 1_000.0, date = Instant.DISTANT_PAST),
        )
    )
    val spendings: Flow<List<Spending>> = _mutableSpendings

    override fun observeSpendings(start: Instant, end: Instant): Flow<List<Spending>> {
        return spendings
            .map { items -> items.filter { spending -> spending.date in start..end } }
            .flowOn(dispatchers.io())
    }

    override suspend fun addSpending(spending: Spending) {
        withContext(dispatchers.io()) {
            _mutableSpendings.update { old -> old + spending }
        }
    }

    override suspend fun updateSpending(spending: Spending) {
        withContext(dispatchers.io()) {
            _mutableSpendings.update { old ->
                old.map { oldSpending ->
                    if (oldSpending.id == spending.id) spending else oldSpending
                }
            }
        }
    }

    override suspend fun deleteSpending(spending: Spending) {
        withContext(dispatchers.io()) {
            _mutableSpendings.update { old ->
                old.filter { oldSpending -> oldSpending.id != spending.id }
            }
        }
    }

    override suspend fun getSpending(id: KmmUUID): Spending? {
        return withContext(dispatchers.io()) {
            _mutableSpendings.value.find { spending -> spending.id == id }
        }
    }
}