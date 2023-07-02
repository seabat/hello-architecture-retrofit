package dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String?
)