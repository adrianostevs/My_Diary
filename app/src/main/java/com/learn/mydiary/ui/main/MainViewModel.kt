package com.learn.mydiary.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.domain.entity.StoryEntity
import com.learn.mydiary.ui.auth.login.LoginEvent
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mStoryRepository: StoryRepository) :
    ViewModel(), IStoryEvent {

    private val _storyEvent by lazy { Channel<StoryEvent>() }
    val storyEvent: Flow<StoryEvent>
        get() = _storyEvent.receiveAsFlow()

    override fun getStory(storyRequest: StoryRequest): Job = viewModelScope.launch {
        _storyEvent.send(StoryEvent.StoryLoading(true))
        mStoryRepository.getAllStory(storyRequest).onCompleteListener(
            onSuccess = { data ->
                Log.d("FFF", "Success $data")
                data?.collectLatest { _storyEvent.send(StoryEvent.StorySuccess(it)) }
                _storyEvent.send(StoryEvent.StoryLoading(false))
            },
            onFailure = { _, _ ->
                _storyEvent.send(StoryEvent.StoryLoading(false))
                Log.d("FFF", "Failuer")
            },
            onError = {
                _storyEvent.send(StoryEvent.StoryFailed(it.message))
                _storyEvent.send(StoryEvent.StoryLoading(false))
                Log.d("FFF", "Error")
            }
        )
    }


}