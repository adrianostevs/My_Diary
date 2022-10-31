package com.learn.mydiary.ui.main

import androidx.paging.PagingData
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.domain.model.Story
import kotlinx.coroutines.Job

sealed class StoryEvent {
    data class StoryLoading(val isLoading: Boolean): StoryEvent()
    data class StoryFailed(val message: String?): StoryEvent()
    data class StorySuccess(val data: PagingData<Story>): StoryEvent()
}

interface IStoryEvent{
    fun getStory(storyRequest: StoryRequest): Job
}