package com.learn.mydiary.ui.addstory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.mydiary.data.remote.model.request.AddStoryRequest
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val mStoryRepository: StoryRepository) :
    ViewModel(), IAddStoryEvent {

    private val _addStoryEvent by lazy { Channel<AddStoryEvent>() }
    val storyEvent: Flow<AddStoryEvent>
        get() = _addStoryEvent.receiveAsFlow()

    override fun setStory(addStoryRequest: AddStoryRequest): Job = viewModelScope.launch {
        val photo = addStoryRequest.photo

        val part = MultipartBody.Builder()
        part.setType(MultipartBody.FORM)

        if (photo != null)
            part.addFormDataPart(
                "photo",
                addStoryRequest.photo.name,
                photo.asRequestBody("image/*".toMediaTypeOrNull())
            )
        part.addFormDataPart(
            "description",
            addStoryRequest.description ?: ""
        )

        _addStoryEvent.send(AddStoryEvent.AddStoryLoading(true))
        mStoryRepository.setStory(part.build()).onCompleteListener(
            onSuccess = {
                _addStoryEvent.send(AddStoryEvent.AddStoryLoading(false))
                _addStoryEvent.send(AddStoryEvent.AddStorySuccess(it?.flowAsData()))
            },
            onError = {
                _addStoryEvent.send(AddStoryEvent.AddStoryLoading(false))
                _addStoryEvent.send(AddStoryEvent.AddStoryFailed(it.message))
            },
            onFailure = { _, data ->
                _addStoryEvent.send(AddStoryEvent.AddStoryLoading(false))
                _addStoryEvent.send(AddStoryEvent.AddStoryFailed(data?.flowAsData()?.message))
            }
        )
    }
}