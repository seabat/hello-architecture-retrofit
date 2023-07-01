package dev.seabat.android.helloarchitectureretrofit.domain.entity

class RepositoryEntity(
    val name: String,
    val full_name: String,
    val html_url: String,
    val description: String?,
    val created_at: String,
    val owner: OwnerEntity
)
