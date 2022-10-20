package com.learn.mydiary.domain.usecase

import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.domain.model.Base
import com.learn.mydiary.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun login(loginRequest: LoginRequest): AppResult<Flow<LoginResultResponse?>>
//    suspend fun register(registerRequest: RegisterRequest): AppResult<Flow<Base>>
}