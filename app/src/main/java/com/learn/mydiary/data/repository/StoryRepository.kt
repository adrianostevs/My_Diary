package com.learn.mydiary.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.learn.mydiary.data.remote.StoryDataSource
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.util.wrapIdlingResource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class StoryRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllStories(): Flow<PagingData<Story>> {

        wrapIdlingResource {
            return Pager(
                config = PagingConfig(
                    pageSize = 15
                ),
                pagingSourceFactory = { StoryDataSource(apiService) },
            ).flow
        }
    }

    fun setStory(
        description: RequestBody,
        image: MultipartBody.Part
    ): LiveData<ResultResponse<BaseResponse>> = liveData {
        emit(ResultResponse.Loading(isLoading = true))
        try {
            val response = apiService.setStories(description, image)
            if (!response.error) {
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

    fun getLocation(): LiveData<ResultResponse<ListStoryResponse>> =
        liveData {
            emit(ResultResponse.Loading(isLoading = true))
            try {
                val response = apiService.getStories(1, 30, 1)
                if (!response.error!!) {
                    emit(ResultResponse.Success(response))
                    emit(ResultResponse.Loading(isLoading = false))
                } else {
                    emit(ResultResponse.Loading(isLoading = false))
                    emit(ResultResponse.Failure(response.message!!))
                }
            } catch (e: Exception) {
                emit(ResultResponse.Failure(e.message.toString()))
                emit(ResultResponse.Loading(isLoading = false))
            }
        }

}