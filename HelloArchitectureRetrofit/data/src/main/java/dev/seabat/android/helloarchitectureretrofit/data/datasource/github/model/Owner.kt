package dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model

import com.squareup.moshi.Json

data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String?,
)