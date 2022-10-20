package com.learn.mydiary.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StoryEntity(
    var id: String?,
    var name: String?,
    var description: String?,
    var photoUrl: String?,
    var createdAt: String?,
    var lat: Float? = null,
    var lon: Float? = null
)
