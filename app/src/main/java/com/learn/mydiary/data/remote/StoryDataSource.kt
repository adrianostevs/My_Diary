package com.learn.mydiary.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.util.preferences.Preferences
import javax.inject.Inject

class StoryDataSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, Story>() {

    private lateinit var mStoryRequest: StoryRequest

    fun setRequest(storyRequest: StoryRequest){
        mStoryRequest = storyRequest
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getStories(mStoryRequest.copy(page = position))

            val data = ArrayList<Story>()
            response.body()?.listStory?.map {
                data.add(
                    Story(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        photoUrl = it.photoUrl,
                    )
                )
            }
            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}