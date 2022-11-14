package com.learn.mydiary.ui.addstory

import androidx.lifecycle.ViewModel
import com.learn.mydiary.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val mStoryRepository: StoryRepository) :
    ViewModel() {

    fun setStory(
        description: RequestBody,
        image: MultipartBody.Part,
    ) = mStoryRepository.setStory(description, image)

}