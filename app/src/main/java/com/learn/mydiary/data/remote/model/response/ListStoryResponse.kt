package com.learn.mydiary.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
    val lat: Float? = null,
    @Expose
    @SerializedName("lon")
    val lon: Float? = null
)

