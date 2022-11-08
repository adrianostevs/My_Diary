package com.learn.mydiary.repository.user

import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStory
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.data.remote.network.ApiService
import okhttp3.RequestBody
import retrofit2.Response

class ApiServiceTest : ApiService {
    override suspend fun register(registerRequest: RegisterRequest): Response<BaseResponse> =
        Response.success(dummyRegister)

    override suspend fun login(loginRequest: LoginRequest): Response<LoginResultResponse> =
        Response.success(dummyLogin)

    override suspend fun getStories(storyRequest: StoryRequest): Response<ListStoryResponse> =
        Response.success(dummyStory)

    override suspend fun setStories(requestBody: RequestBody): Response<BaseResponse> =
        Response.success(dummySetStory)

    companion object {

        val dummyLogin = LoginResultResponse(
            error = false,
            message = "success",
            loginResult = LoginResultResponse.LoginResult(
                userId = "user-GnfHKnXLPfnnQ_PL",
                name = "Steven",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUduZkhLblhMUGZublFfUEwiLCJpYXQiOjE2Njc2NzAyMDZ9.h-wRZ01z3zQEUXdJhrh7hXJhd9wFjmbwGI34zRRdnkQ"
            )
        )

        val dummyRegister = BaseResponse(
            error = false,
            message = "success"
        )

        val dummyStory = ListStoryResponse(
            error = false,
            message = "success",
            listStory = arrayListOf(
                ListStory(
                    id = "story-t_3Hcy48HaWACzSz",
                    lat = 200.0,
                    lon = 192.8,
                    description = "blablabla",
                    name = "Steven",
                    createdAt = "2022-11-05T15:36:16.698Z",
                    photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1667662576694_l0lB-JvS.jpg"
                ),
                ListStory(
                    id = "story-t_3Hcy48HaWACzSz",
                    lat = 200.0,
                    lon = 192.8,
                    description = "blablabla",
                    name = "Steven",
                    createdAt = "2022-11-05T15:36:16.698Z",
                    photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1667662576694_l0lB-JvS.jpg"
                )
            )
        )

        val dummySetStory = BaseResponse(
            error = false,
            message = "success"
        )
    }
}