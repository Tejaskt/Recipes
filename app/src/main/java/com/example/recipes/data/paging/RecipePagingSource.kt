package com.example.recipes.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipes.data.remote.api.RecipeApi
import com.example.recipes.data.remote.mapper.toDomain
import com.example.recipes.domain.model.Recipe
import okio.IOException
class RecipePagingSource(
    private val api: RecipeApi,
    private val category: String
) : PagingSource<Int, Recipe>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Recipe> {

        val page = params.key ?: 0
        val limit = params.loadSize
        val skip = page * limit

        return try {
            val response = api.getRecipes(
                limit = limit,
                skip = skip
            )

            if (!response.isSuccessful) {
                return LoadResult.Error(Throwable("API error"))
            }

            val body = response.body()
                ?: return LoadResult.Error(Throwable("Empty response"))

            //  (PER PAGE) FILTER HERE
            val filtered = body.recipes
                .filter { recipe ->
                    category == "All" ||
                            recipe.mealType.any {
                                it.equals(category, ignoreCase = true)
                            }
                }
                .map { it.toDomain() }

            LoadResult.Page(
                data = filtered,
                prevKey = if (page == 0) null else page - 1,
                nextKey =
                    if (filtered.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, Recipe>
    ): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}
