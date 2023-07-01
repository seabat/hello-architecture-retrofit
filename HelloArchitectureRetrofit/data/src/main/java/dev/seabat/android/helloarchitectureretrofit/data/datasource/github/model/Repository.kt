package dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model

data class Repository(
    val name: String,
    val full_name: String,
    val html_url: String,
    val description: String?,
    val created_at: String,
    val owner: Owner
)
