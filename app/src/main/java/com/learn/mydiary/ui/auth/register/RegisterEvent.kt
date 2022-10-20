package com.learn.mydiary.ui.auth.register

import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import kotlinx.coroutines.Job

sealed class RegisterEvent{

    data class RegisterLoading(val isLoading: Boolean) : RegisterEvent()
    data class RegisterFailed(val message: String) : RegisterEvent()
    data class RegisterSuccess(val register: BaseResponse?) : RegisterEvent()
}

interface IRegisterEvent{
    fun register(registerRequest: RegisterRequest) : Job
}
