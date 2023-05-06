package dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model

data class Repository(val name: String, val description: String?, val created_at: String, val owner: Owner)
