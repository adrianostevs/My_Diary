package com.learn.mydiary.data.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @Expose
    @SerializedName("error")
    val error: Boolean,
    @Expose
    @SerializedName("message")
    val message: String,
)