package com.learn.mydiary.domain.repository

import androidx.paging.PagingData
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.domain.model.Story
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IStoryRepository {

    suspend fun getAllStory(storyRequest: StoryRequest) : AppResult<Flow<PagingData<Story>>>

    suspend fun setStory(requestBody: RequestBody): AppResult<Flow<BaseResponse?>>

    suspend fun getLocation(storyRequest: StoryRequest) : AppResult<Flow<ListStoryResponse?>>

    suspend fun getAllStoryTest(storyRequest: StoryRequest) : AppResult<Flow<ListStoryResponse?>>
}