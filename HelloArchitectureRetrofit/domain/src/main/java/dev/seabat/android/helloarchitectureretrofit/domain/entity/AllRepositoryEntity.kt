package dev.seabat.android.helloarchitectureretrofit.domain.entity

data class AllRepositoryEntity(
    val page: Int,
    val totalPage: Int,
    val totalCount: Int,
    val repos: RepositoryListEntity
)
