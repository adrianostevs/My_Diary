package com.learn.mydiary.data.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListStoryResponse(
    @Expose
    @SerializedName("error")
    val error: Boolean?,
    @Expose
    @SerializedName("message")
    val message: String?,
    @Expose
    @SerializedName("listStory")
    val listStory: List<ListStory>,
)

data class ListStory(
    @Expose
    @SerializedName("id")
    val id: String?,
    @Expose
    @SerializedName("name")
    val name: String?,
    @Expose
    @SerializedName("description")
    val description: String?,
    @Expose
    @SerializedName("photoUrl")
    val photoUrl: String?,
    @Expose
    @SerializedName("createdAt")
    val createdAt: String?,
    @Expose
    @SerializedName("lat")
    val lat: Double? = null,
    @Expose
    @SerializedName("lon")
    val lon: Double? = null
)

