package com.gthio.spendykmm.domain

import kotlin.coroutines.CoroutineContext

expect class DispatcherProvider() {
    fun main(): CoroutineContext
    fun io(): CoroutineContext
    fun default(): CoroutineContext
    fun unconfined(): CoroutineContext
}