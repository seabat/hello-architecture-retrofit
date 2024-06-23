package dev.seabat.android.pagingarchitectureretrofit.domain.entity

data class RepositoryEntity(
    val id: Int,
    val name: String,
    val fullName: String,
    val htmlUrl: String,
    val description: String?,
    val createdAt: String,
    val owner: OwnerEntity
)
