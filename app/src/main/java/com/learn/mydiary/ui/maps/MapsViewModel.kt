package com.learn.mydiary.ui.maps

import androidx.lifecycle.ViewModel
import com.learn.mydiary.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val mStoryRepository: StoryRepository) : ViewModel(){

    fun getAllLocation() = mStoryRepository.getLocation()

}