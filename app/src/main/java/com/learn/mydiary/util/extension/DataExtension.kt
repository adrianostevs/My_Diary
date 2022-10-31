package com.learn.mydiary.util.extension

import com.google.gson.Gson
import com.learn.mydiary.base.AppResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <T> AppResult<T>.onCompleteListener(
    onSuccess: (T?) -> Unit,
    onFailure: (Int?, T?) -> Unit,
    onError: (Throwable) -> Unit
) {
    when (this) {
        is AppResult.OnSuccess -> onSuccess(this.data)
        is AppResult.OnFailure -> onFailure(this.code, this.data)
        is AppResult.OnError -> onError(this.throwable)
    }
}

inline fun <T> connection(run: () -> AppResult<T>): AppResult<T> =
    try {
        run()
    } catch (e: Throwable) {
        AppResult.OnError(e)
    }

inline fun <reified T> Response<T>.valueAsFlow(): AppResult<Flow<T?>> {
    return if (this.isSuccessful) {
        AppResult.OnSuccess(flow { emit(this@valueAsFlow.body()) }.flowOn(Dispatchers.IO))
    }
    else {
        val data = Gson().fromJson(errorBody()?.string(), T::class.java)
        AppResult.OnFailure(code = this.code(), data = flow { emit(data) }.flowOn(Dispatchers.IO))
    }
}

suspend fun <T> Flow<T>.flowAsData(): T? {
    val job = CoroutineScope(Dispatchers.IO).async {
        var value: T? = null

        this@flowAsData.collectLatest { value = it }

        value
    }

    return job.await()
}