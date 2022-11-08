package com.learn.mydiary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.StoryDataSource
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.domain.repository.IStoryRepository
import com.learn.mydiary.util.extension.connection
import com.learn.mydiary.util.extension.valueAsFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject

class StoryRepository @Inject constructor(private val apiService: ApiService) :
    IStoryRepository {

    override suspend fun getAllStory(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>> =
        connection {
            AppResult.OnSuccess(
                Pager(PagingConfig(pageSize = storyRequest.size)) {
                    StoryDataSource(apiService).apply { setRequest(storyRequest) }
                }.flow
            )
        }

    override suspend fun setStory(requestBody: RequestBody): AppResult<Flow<BaseResponse?>> =
        connection {
            apiService.setStories(requestBody).valueAsFlow()
        }

    override suspend fun getLocation(storyRequest: StoryRequest): AppResult<Flow<ListStoryResponse?>> =
        connection {
            apiService.getStories(storyRequest).valueAsFlow()
        }

    override suspend fun getAllStoryTest(storyRequest: StoryRequest): AppResult<Flow<ListStoryResponse?>> =
        connection {
            apiService.getStories(storyRequest).valueAsFlow()
        }


}