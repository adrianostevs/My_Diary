package com.learn.mydiary.data.remote.model.request

data class StoryRequest(
    var page: Int = 1,
    var storySize: Int = 15,
    var location: Int = 0
) : Map<String, Int> by mapOf(
    "page" to page,
    "size" to storySize,
    "location" to location
)
