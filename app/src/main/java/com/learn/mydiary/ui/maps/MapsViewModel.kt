package com.learn.mydiary.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val mStoryRepository: StoryRepository) : ViewModel(), IMapsEvent{

    private val _mapsEvent by lazy { Channel<MapsEvent>() }
    val mapsEvent: Flow<MapsEvent>
        get() = _mapsEvent.receiveAsFlow()

    override fun getAllLocation(storyRequest: StoryRequest): Job = viewModelScope.launch{
        _mapsEvent.send(MapsEvent.GetLocationLoading(true))
        mStoryRepository.getLocation(storyRequest).onCompleteListener(
            onSuccess = {
                _mapsEvent.send(MapsEvent.GetLocationLoading(false))
                _mapsEvent.send(MapsEvent.GetLocationSuccess(it?.flowAsData()))
            },
            onFailure = {_,data ->
                _mapsEvent.send(MapsEvent.GetLocationLoading(false))
                _mapsEvent.send(MapsEvent.GetLocationFailed(data?.flowAsData()?.message))
            },
            onError = {
                _mapsEvent.send(MapsEvent.GetLocationLoading(false))
                _mapsEvent.send(MapsEvent.GetLocationFailed(it.message))
            }
        )

    }

}