package com.learn.mydiary.domain.interactor

import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.domain.model.Base
import com.learn.mydiary.domain.model.User
import com.learn.mydiary.domain.repository.IUserRepository
import com.learn.mydiary.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase{
    override suspend fun login(loginRequest: LoginRequest): AppResult<Flow<LoginResultResponse?>> = userRepository.login(loginRequest)
//    override suspend fun register(registerRequest: RegisterRequest): AppResult<Flow<Base>> = userRepository.register(registerRequest)
}