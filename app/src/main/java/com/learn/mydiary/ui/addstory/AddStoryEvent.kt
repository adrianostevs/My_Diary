package com.learn.mydiary.ui.addstory

import com.learn.mydiary.data.remote.model.request.AddStoryRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import kotlinx.coroutines.Job

sealed class AddStoryEvent {
    data class AddStoryLoading(val isLoading: Boolean): AddStoryEvent()
    data class AddStoryFailed(val message: String?): AddStoryEvent()
    data class AddStorySuccess(val response: BaseResponse?): AddStoryEvent()
}

interface IAddStoryEvent{
    fun setStory(addStoryRequest: AddStoryRequest): Job
}