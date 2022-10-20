package com.learn.mydiary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val message: String? = null,
    val id: String?,
    val name: String?,
    val description: String?,
    val photoUrl: String?,
    val createdAt: String? = null,
    val lat: Float? = null,
    val lon: Float? = null,
): Parcelable
