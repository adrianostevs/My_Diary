package com.learn.mydiary.data.remote.model.response

sealed class ResultResponse<out T: Any?> {
    class Success<out T: Any?>(val data: T) : ResultResponse<T>()

    class Failure(val errorMessage: String) : ResultResponse<Nothing>()

    class Loading(val isLoading: Boolean) : ResultResponse<Nothing>()
}