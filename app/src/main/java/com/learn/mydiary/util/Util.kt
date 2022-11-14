package com.learn.mydiary.util

import androidx.test.espresso.idling.CountingIdlingResource

object espressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapIdlingResource(function: () -> T): T {
    espressoIdlingResource.increment() // Set app as busy.
    return try {
        function()
    } finally {
        espressoIdlingResource.decrement() // Set app as idle.
    }
}