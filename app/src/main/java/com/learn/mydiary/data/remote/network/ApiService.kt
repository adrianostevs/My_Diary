package com.learn.mydiary.data.remote.network

import com.learn.mydiary.data.remote.model.request.AddStoryRequest
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStory
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<BaseResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResultResponse>

    @GET("stories")
    suspend fun getStories(@QueryMap storyRequest: StoryRequest): Response<ListStoryResponse>

    @POST("stories")
    suspend fun setStories(@Body addStoryRequest: AddStoryRequest): Response<BaseResponse>

}