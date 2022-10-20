package com.learn.mydiary.data.repository

import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.repository.IUserRepository
import com.learn.mydiary.util.extension.connection
import com.learn.mydiary.util.extension.valueAsFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
): IUserRepository{

    override suspend fun login(loginRequest: LoginRequest): AppResult<Flow<LoginResultResponse?>> = connection {
        apiService.login(loginRequest).valueAsFlow()
    }

    override suspend fun register(registerRequest: RegisterRequest): AppResult<Flow<BaseResponse?>> = connection{
        apiService.register(registerRequest).valueAsFlow()
    }

}