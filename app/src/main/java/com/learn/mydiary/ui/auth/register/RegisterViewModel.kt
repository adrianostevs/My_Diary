package com.learn.mydiary.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel(), IRegisterEvent {

    private val _registerEvent by lazy { Channel<RegisterEvent>() }
    val registerEvent: Flow<RegisterEvent>
        get() = _registerEvent.receiveAsFlow()

    override fun register(registerRequest: RegisterRequest): Job = viewModelScope.launch {
        _registerEvent.send(RegisterEvent.RegisterLoading(true))
        userRepository.register(registerRequest).onCompleteListener(
            onSuccess = {
                _registerEvent.send(RegisterEvent.RegisterSuccess(it?.flowAsData()))
                _registerEvent.send(RegisterEvent.RegisterLoading(false))
            },
            onError = {
                _registerEvent.send(RegisterEvent.RegisterLoading(false))
            },
            onFailure = {_,data ->
                _registerEvent.send(RegisterEvent.RegisterLoading(false))
                _registerEvent.send(RegisterEvent.RegisterFailed(data?.flowAsData()?.message ?: ""))
            },
        )
    }


}