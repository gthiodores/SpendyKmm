package com.gthio.spendykmm.domain

import platform.Foundation.NSUUID

actual class KmmUUID(private val uuid: NSUUID) {
    actual constructor() : this(NSUUID())

    actual val uuidString: String
        get() = uuid.UUIDString
    actual val hashValue: Int
        get() = uuid.hashCode()

    actual companion object {
        actual fun fromString(uuidString: String): KmmUUID {
            return KmmUUID(NSUUID(uUIDString = uuidString))
        }
    }

}