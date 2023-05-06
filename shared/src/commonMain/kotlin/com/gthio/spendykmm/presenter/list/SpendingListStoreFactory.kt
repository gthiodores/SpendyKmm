package com.gthio.spendykmm.presenter.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.gthio.spendykmm.domain.Spending
import com.gthio.spendykmm.domain.SpendingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

internal class SpendingListStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: SpendingRepository,
) {
    fun create(): SpendingListStore {
        return object :
            SpendingListStore,
            Store<SpendingListIntent, SpendingListState, SpendingListEffect>
            by storeFactory.create(
                name = "SpendingList",
                bootstrapper = SimpleBootstrapper(Unit),
                initialState = SpendingListState(),
                executorFactory = ::ListExecutor,
                reducer = ListReducer,
            ) {}
    }

    private sealed interface Message {
        data class SpendingsUpdated(val spendings: List<Spending>) : Message
        data class MarkToggled(val isActive: Boolean) : Message
        data class SpendingMarked(val spending: Spending) : Message
        data class SpendingUnmarked(val spending: Spending) : Message
        object MarkedDeleted : Message
        object DeletingMarked : Message
    }

    private inner class ListExecutor : CoroutineExecutor<
            SpendingListIntent,
            Unit,
            SpendingListState,
            Message,
            SpendingListEffect>() {

        private var job: Job? = null

        private fun getTimeTodayMidnight(): Instant {
            return Clock
                .System
                .todayIn(TimeZone.currentSystemDefault())
                .atStartOfDayIn(TimeZone.currentSystemDefault())
        }

        private fun getTomorrowMidnight(): Instant {
            return getTimeTodayMidnight()
                .plus(
                    value = 1,
                    unit = DateTimeUnit.DAY,
                    timeZone = TimeZone.currentSystemDefault()
                )
        }

        override fun executeAction(action: Unit, getState: () -> SpendingListState) {
            handleFilterChanged(start = null, end = null)
        }

        override fun executeIntent(intent: SpendingListIntent, getState: () -> SpendingListState) {
            when (intent) {
                SpendingListIntent.DeleteMarkedSpendings -> handleDeleteMarkedSpending(getState())

                is SpendingListIntent.LoadSpendings -> handleFilterChanged(
                    start = intent.start,
                    end = intent.end
                )

                is SpendingListIntent.MarkSpendingToDelete -> dispatch(
                    Message.SpendingMarked(intent.spending)
                )

                is SpendingListIntent.UnmarkSpendingToDelete -> dispatch(
                    Message.SpendingUnmarked(
                        intent.spending
                    )
                )

                is SpendingListIntent.SpendingSelected -> publish(
                    SpendingListEffect.NavigateToEdit(intent.id)
                )

                is SpendingListIntent.ToggleMarkDelete -> dispatch(Message.MarkToggled(intent.isActive))
                SpendingListIntent.AddSpending -> publish(SpendingListEffect.NavigateToAdd)
            }
        }

        private fun handleDeleteMarkedSpending(state: SpendingListState) {
            scope.launch {
                dispatch(Message.DeletingMarked)
                state.spendings.forEach { item -> repository.deleteSpending(item) }
                dispatch(Message.MarkedDeleted)
            }
        }

        private fun handleFilterChanged(start: Instant?, end: Instant?) {
            job?.cancel()
            job = repository
                .observeSpendings(
                    start = start ?: getTimeTodayMidnight(),
                    end = end ?: getTomorrowMidnight(),
                )
                .onEach { spendings -> dispatch(Message.SpendingsUpdated(spendings)) }
                .launchIn(scope)
        }

        override fun dispose() {
            job?.cancel()
            job = null
            super.dispose()
        }
    }

    private object ListReducer : Reducer<SpendingListState, Message> {
        override fun SpendingListState.reduce(msg: Message): SpendingListState {
            return when (msg) {
                Message.DeletingMarked -> copy(isLoading = false)

                is Message.MarkToggled -> copy(isMarkActive = msg.isActive)

                Message.MarkedDeleted -> copy(
                    spendingMarkedForDeletion = emptyList(),
                    isMarkActive = false,
                    isLoading = false
                )

                is Message.SpendingMarked -> copy(spendingMarkedForDeletion = spendingMarkedForDeletion + msg.spending)
                is Message.SpendingUnmarked -> copy(spendingMarkedForDeletion = spendingMarkedForDeletion.filter { spend -> spend.id.hashValue == msg.spending.id.hashValue })
                is Message.SpendingsUpdated -> copy(spendings = msg.spendings)
            }
        }
    }
}