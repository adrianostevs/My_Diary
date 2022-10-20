package com.learn.mydiary.data.remote.network

import com.learn.mydiary.util.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpClientInterceptor @Inject constructor(val preferences: Preferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val preference = preferences.getValue(Preferences.TOKEN)
        if (preference != null)
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $preference"
            )

        return chain.proceed(requestBuilder.build())
    }

}