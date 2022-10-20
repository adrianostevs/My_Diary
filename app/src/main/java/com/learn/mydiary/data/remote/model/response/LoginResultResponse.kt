package com.learn.mydiary.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResultResponse(
    @Expose
    @SerializedName("error")
    val error: Boolean,
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("loginResult")
    val loginResult: LoginResult? = null,

): Parcelable {
    @Parcelize
    data class LoginResult(
        @Expose
        @SerializedName("userId")
        val userId: String?,
        @Expose
        @SerializedName("name")
        val name: String?,
        @Expose
        @SerializedName("token")
        val token: String?
    ): Parcelable
}
