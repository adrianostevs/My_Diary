package com.learn.mydiary.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mStoryRepository: StoryRepository) :
    ViewModel(){

    fun getStory(): LiveData<PagingData<Story>>{
        return  mStoryRepository.getAllStories().cachedIn(viewModelScope).asLiveData()
    }

}