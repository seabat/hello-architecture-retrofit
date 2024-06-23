package dev.seabat.android.pagingarchitectureretrofit.domain.usecase

import android.nfc.tech.MifareUltralight
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.repository.GithubRepositoryContract
import kotlinx.coroutines.flow.Flow

class CreateRepositoryPagingSourceUseCase(private val githubRepository: GithubRepositoryContract) : CreateRepositoryPagingSourceUseCaseContract {

    override fun invoke(query: String?): Flow<PagingData<RepositoryEntity>> {
        return  Pager(
            config = PagingConfig(pageSize = MifareUltralight.PAGE_SIZE,enablePlaceholders = false),
            pagingSourceFactory = { githubRepository.repositoryPagingSource(query ?: "architecture") }
        ).flow
    }
}