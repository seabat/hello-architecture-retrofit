package dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model

/**
 * API https://api.github.com/search/repositories のレスポンス
 *
 * 以下のデータを受信する
 * {
 *   "total_count": 1948,
 *   "incomplete_results": false,
 *   "items": [...]
 * }
 */
data class GetAllRepoResponse(
    val total_count: Int,
    val items: List<Repository>,
)
// NOTE: ArrayList を使用すると「Moshi only supports the collection interfaces by default」
//      というエラーが発生するので List を使用すること