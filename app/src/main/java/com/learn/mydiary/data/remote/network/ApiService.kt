package com.learn.mydiary.data.remote.network

import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): BaseResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResultResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int?
    ): ListStoryResponse

    @Multipart
    @POST("stories")
    suspend fun setStories(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ): BaseResponse

}