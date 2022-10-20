package com.learn.mydiary.domain.repository

import androidx.paging.PagingData
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.AddStoryRequest
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.domain.model.Base
import com.learn.mydiary.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {

    suspend fun getAllStory(storyRequest: StoryRequest) : AppResult<Flow<PagingData<Story>>>

    suspend fun setStory(addStoryRequest: AddStoryRequest): AppResult<Flow<Base>>
}