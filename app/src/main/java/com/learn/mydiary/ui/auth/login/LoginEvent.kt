package com.learn.mydiary.ui.auth.login

import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import kotlinx.coroutines.Job

sealed class LoginEvent {

    data class LoginLoading(val isLoading: Boolean) : LoginEvent()
    data class LoginSuccess(val login: LoginResultResponse?) : LoginEvent()
    data class LoginFailure(val message: String?) : LoginEvent()

}

interface ILoginEvent {
    fun login(loginRequest: LoginRequest) : Job
}