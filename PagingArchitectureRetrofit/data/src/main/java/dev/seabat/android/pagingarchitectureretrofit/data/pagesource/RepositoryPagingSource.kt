package dev.seabat.android.pagingarchitectureretrofit.data.pagesource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubExceptionConverter
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.model.Repository
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.OwnerEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.exception.AppException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.math.max

private const val STARTING_KEY = 0
private const val PAGE_SIZE = 30

class RepositoryPagingSource(
    private val query: String,
    private val endpoint: GithubApiService
) : PagingSource<Int, RepositoryEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryEntity> {
        val startKey = params.key ?: STARTING_KEY

        // 0..29 => 1ページ目、30..59 => 2ページ目、60..89 => 3ページ目
        val page = (startKey / PAGE_SIZE) + 1

        Log.d("PAR_PAGING", "startKey: $startKey, page: $page")

        try {
            val repos = fetchRepos(query, startKey, page)
            return LoadResult.Page(
                data = repos?.list ?: emptyList(),
                prevKey = calculatePrevKey(startKey, repos?.size ?: 0),
                nextKey = calculateNextKey(startKey, repos?.size ?: 0)
            )
        } catch(ex: Exception) {
            return LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val repository = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = repository.id - (PAGE_SIZE / 2))
    }

    /**
     * リポジトリ一覧を取得できる Web API を呼び出す
     *
     * - Web API の実行が正常終了し、レスポンスデータのデータが null の場合、null を返す。
     */
    private suspend fun fetchRepos(query: String, startKey: Int, page: Int?): RepositoryListEntity? {
        //NOTE: 同期方式の場合はメインスレッド以外で通信する必要あり
        return withContext(Dispatchers.IO) {
            val response = try {
                // 同期方式で HTTP 通信を行う
                endpoint.getAllRepo(query, page, PAGE_SIZE).execute()
            } catch (e: Exception) { // 通信自体が失敗した場合
                val exception = AppException.convertTo(e as Throwable)
                throw exception
            }

            val repositories = if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.items?.let { items ->
                    convertToEntity(startKey, items)
                }
            } else {
                val exception = GithubExceptionConverter.convertTo(
                    response.code(),
                    response.errorBody()?.string()
                )
                throw exception
            }

            repositories
        }
    }

    /**
     * API のレスポンスを Entity に変換する
     */
    private fun convertToEntity(startKey: Int, repos: List<Repository>): RepositoryListEntity {
        return RepositoryListEntity(
                repos.mapIndexed { index, value ->
                    // NOTE: id は 1 開始とする。
                    //       startKey と index の開始値は 0 なので、 id に +1 する。
                    val id = 1 + startKey + index

                    Log.d("PAR_PAGING", "id: ${id}, name: ${value.name}")

                    RepositoryEntity(
                        id = id,
                        name = value.name,
                        fullName = value.fullName,
                        htmlUrl = value.htmlUrl,
                        description = value.description,
                        createdAt = value.createdAt,
                        owner = OwnerEntity(avatarUrl = value.owner.avatarUrl)
                    )
                } as ArrayList<RepositoryEntity>
            )
    }

    /**
     * 前のページの開始キーを計算する
     *
     * - ページ 0 の開始キーは null にするのは Paging の仕様。
     * - 現在の開始キーが 0 の場合、 問答無用で前のページの開始キーは null 。
     * - 現在の開始キーが 15 の場合で、サイズが 30 の場合、前のページの開始キーは null となる。
     * - 現在の開始キーが 30 の場合で、サイズが 30 の場合、前のページの開始キーは null となる。
     * - 現在の開始キーが 60 の場合で、サイズが 30 の場合、次のページの開始キーは 30 となる。
     */
    private fun calculatePrevKey(startKey: Int, size: Int): Int? {
        return when (startKey) {
            STARTING_KEY -> null
            else -> when (val prevKey = ensureValidKey(key = startKey - size)) {
                STARTING_KEY -> null
                else -> prevKey
            }
        }
    }

    /**
     * 次のページの開始キーを計算する
     *
     * - 現在の開始キーが 0 の場合で、サイズが 30 の場合、次のページの開始キーは 30 となる。
     * - 最後まで読み込んだ場合は size 0 として受け取り、、次のページの開始キーは null となる。
     */
    private fun calculateNextKey(startKey: Int, size: Int): Int? {
        return when (size) {
            0 -> null
            else -> startKey + size
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}