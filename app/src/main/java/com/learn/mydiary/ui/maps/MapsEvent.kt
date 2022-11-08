package com.learn.mydiary.ui.maps

import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import kotlinx.coroutines.Job

sealed class MapsEvent {
    data class GetLocationSuccess(val data: ListStoryResponse?): MapsEvent()
    data class GetLocationFailed(val message: String?): MapsEvent()
    data class GetLocationLoading(val isLoading: Boolean): MapsEvent()
}

interface IMapsEvent{
    fun getAllLocation(storyRequest: StoryRequest): Job
}