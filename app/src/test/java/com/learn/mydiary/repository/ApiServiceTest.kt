package com.learn.mydiary.repository

import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStory
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.data.remote.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiServiceTest: ApiService {

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

    override suspend fun register(registerRequest: RegisterRequest): BaseResponse {
        return dummyRegister
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResultResponse {
        return dummyLogin
    }

    override suspend fun getStories(page: Int, size: Int, location: Int?): ListStoryResponse {
        return dummyStory
    }

    override suspend fun setStories(
        description: RequestBody,
        file: MultipartBody.Part
    ): BaseResponse {
        return dummyRegister
    }
}