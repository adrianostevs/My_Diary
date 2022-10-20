package com.learn.mydiary.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.StoryDataSource
import com.learn.mydiary.data.remote.model.request.AddStoryRequest
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.ListStory
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.entity.StoryEntity
import com.learn.mydiary.domain.model.Base
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.domain.repository.IStoryRepository
import com.learn.mydiary.util.extension.connection
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StoryRepository @Inject constructor(private val apiService: ApiService) :
    IStoryRepository {

    override suspend fun getAllStory(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>> =
        connection {
            Log.d(
                "FFFFFF", "CEK DATA ${
                    Pager(PagingConfig(pageSize = storyRequest.size)) {
                        StoryDataSource(apiService).apply { setRequest(storyRequest) }
                    }.flow
                }"
            )
            AppResult.OnSuccess(
                Pager(PagingConfig(pageSize = storyRequest.size)) {
                    StoryDataSource(apiService).apply { setRequest(storyRequest) }
                }.flow
            )
        }

    override suspend fun setStory(addStoryRequest: AddStoryRequest): AppResult<Flow<Base>> {
        TODO("Not yet implemented")
    }


}