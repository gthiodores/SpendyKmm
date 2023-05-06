package com.gthio.spendykmm.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlin.coroutines.CoroutineContext

actual class DispatcherProvider actual constructor() {
    actual fun main(): CoroutineContext = Dispatchers.Main

    actual fun io(): CoroutineContext = newFixedThreadPoolContext(100, "IO")

    actual fun default(): CoroutineContext = Dispatchers.Default

    actual fun unconfined(): CoroutineContext = Dispatchers.Unconfined
}