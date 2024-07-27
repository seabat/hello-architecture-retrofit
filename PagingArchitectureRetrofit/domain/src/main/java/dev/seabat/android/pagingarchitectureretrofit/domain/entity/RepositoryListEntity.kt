package dev.seabat.android.pagingarchitectureretrofit.domain.entity

class RepositoryListEntity(val list: ArrayList<RepositoryEntity>) : List<RepositoryEntity> by list {
    fun get(name: String): RepositoryEntity? {
        return list.firstOrNull {
            it.name == name
        }
    }
}