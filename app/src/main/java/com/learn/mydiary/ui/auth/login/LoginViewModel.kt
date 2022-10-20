package com.learn.mydiary.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel(),
    ILoginEvent {

    private val _loginEvent by lazy { Channel<LoginEvent>() }
    val loginEvent: Flow<LoginEvent>
        get() = _loginEvent.receiveAsFlow()

    override fun login(loginRequest: LoginRequest): Job = viewModelScope.launch {
        _loginEvent.send(LoginEvent.LoginLoading(true))
        userRepository.login(loginRequest).onCompleteListener(
            onSuccess = {
                _loginEvent.send(LoginEvent.LoginLoading(false))
                _loginEvent.send(LoginEvent.LoginSuccess(it?.flowAsData()))
            },
            onError = {
                _loginEvent.send(LoginEvent.LoginLoading(false))
                _loginEvent.send(LoginEvent.LoginFailure(it.message))
            },
            onFailure = { _, data ->
                _loginEvent.send(LoginEvent.LoginLoading(false))
                _loginEvent.send(LoginEvent.LoginFailure(data?.flowAsData()?.message))
            }
        )
    }
}