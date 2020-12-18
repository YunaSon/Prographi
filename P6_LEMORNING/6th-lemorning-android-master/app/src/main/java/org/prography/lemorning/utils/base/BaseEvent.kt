package org.prography.lemorning.utils.base

class BaseEvent<T : Any>(var data : T, var handled : Boolean = false) {
    fun get() : T? = when {
        !handled -> {
            handled = true
            data
        }
        else -> null
    }
}