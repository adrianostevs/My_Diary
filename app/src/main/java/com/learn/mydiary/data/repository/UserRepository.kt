package com.learn.mydiary.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.repository.IUserRepository
import com.learn.mydiary.util.extension.connection
import com.learn.mydiary.util.extension.valueAsFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
){

    fun login(loginRequest: LoginRequest): LiveData<ResultResponse<LoginResultResponse>> = liveData {
        emit(ResultResponse.Loading(isLoading = true))
        try {
            val response = apiService.login(loginRequest)
            if (!response.error) {
                emit(ResultResponse.Success(response))
                emit(ResultResponse.Loading(isLoading = false))
            } else {
                emit(ResultResponse.Failure(response.message))
                emit(ResultResponse.Loading(isLoading = false))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Failure(e.message.toString()))
            emit(ResultResponse.Loading(isLoading = false))
        }
    }

    fun register(registerRequest: RegisterRequest): LiveData<ResultResponse<BaseResponse>> = liveData {
        emit(ResultResponse.Loading(isLoading = true))
        try {
            val response = apiService.register(registerRequest)
            if (!response.error){
                emit(ResultResponse.Success(response))
                emit(ResultResponse.Loading(isLoading = false))
            } else {
                emit(ResultResponse.Loading(isLoading = false))
                emit(ResultResponse.Failure(response.message))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Failure(e.message.toString()))
            emit(ResultResponse.Loading(isLoading = false))
        }
    }

}