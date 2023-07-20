package com.learn.mydiary.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.domain.model.Story
import javax.inject.Inject

class StoryDataSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, Story>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(page, params.loadSize, 0)
            val data = ArrayList<Story>()
            responseData.listStory.map {
                data.add(
                    Story(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        photoUrl = it.photoUrl,
                    )
                )
            }

            Log.d("FFFF","$data")

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}