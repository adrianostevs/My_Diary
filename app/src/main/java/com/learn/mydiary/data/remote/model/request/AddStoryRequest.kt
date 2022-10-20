package com.learn.mydiary.data.remote.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File

data class AddStoryRequest(
    @Expose
    @SerializedName("description")
    val description: String?,
    @Expose
    @SerializedName("photo")
    val photo: File? = null,
)
