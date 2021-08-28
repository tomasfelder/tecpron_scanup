package com.tecpron.tecpronscanning.general

import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineStart.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = LAZY) {
            block.invoke(this)
        }
    }
}