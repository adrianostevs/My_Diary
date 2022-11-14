package com.learn.mydiary.data

import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStory
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.domain.model.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DummyData {

    fun dummyLoginResponse(): LoginResultResponse {
        return LoginResultResponse(
            error = false,
            message = "success",
            loginResult = LoginResultResponse.LoginResult(
                userId = "user-GnfHKnXLPfnnQ_PL",
                name = "Steven",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUduZkhLblhMUGZublFfUEwiLCJpYXQiOjE2Njc2NzAyMDZ9.h-wRZ01z3zQEUXdJhrh7hXJhd9wFjmbwGI34zRRdnkQ"
            )
        )
    }

    fun dummyBaseResponse(): BaseResponse {
        return BaseResponse(
            error = false,
            message = "success"
        )
    }

    fun dummyListStory(): List<Story> {
        val data: MutableList<Story> = arrayListOf()
        for (i in 0..20) {
            val story = Story(
                "OK",
                "FIAD1290843",
                "Brokoli",
                "Story Description",
                "link-ini",
            )
            data.add(story)
        }
        return data
    }

    fun dummyStory(): List<ListStory> {
        val data: MutableList<ListStory> = arrayListOf()
        for (i in 0..20) {
            val story = ListStory(
                "OK",
                "FIAD1290843",
                "Brokoli",
                "Story Description",
                "link-ini",
            )
            data.add(story)
        }
        return data
    }

    fun generateDummyStoriesResponse(): ListStoryResponse {
        return ListStoryResponse( false, "success", dummyStory())
    }

    fun dummyListMap(): ListStoryResponse {
        return ListStoryResponse(
            error = false,
            message = "oK",
            listStory = arrayListOf(
                ListStory(
                    id = "j19jrjo01fk10o",
                    name = "Bayam",
                    description = "hahahihi",
                    photoUrl = "linkkkk",
                    createdAt = "2001",
                    lat = 110.00,
                    lon = 221.0
                ),
                ListStory(
                    id = "j19jrjo01fk10o",
                    name = "Bayam",
                    description = "hahahihi",
                    photoUrl = "linkkkk",
                    createdAt = "2001",
                    lat = 110.00,
                    lon = 221.0
                )
            )
        )
    }

    fun dummyMultipart(): MultipartBody.Part {
        val dummyText = "sayur apa ni"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun dummyDescription(): RequestBody {
        val dummyText = "sayur buah"
        return dummyText.toRequestBody()
    }
}