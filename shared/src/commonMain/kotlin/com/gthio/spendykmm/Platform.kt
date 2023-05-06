package com.gthio.spendykmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform