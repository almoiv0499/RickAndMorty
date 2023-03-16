package com.aston.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.aston.data.database.CharacterDatabase
import com.aston.data.model.character.CharacterData
import com.aston.data.remote.CharactersService
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val database: CharacterDatabase,
    private val service: CharactersService
) : PagingSource<Int, CharacterData>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {

        val currentPage = params.key ?: 1

        return try {

            val response = service.fetchCharactersByPage(currentPage)
            val data = response.characterInfo

            database.withTransaction {
                database.characterDao().insertCharacters(data)
            }

            val prevKey = if (currentPage == 1) null else currentPage.minus(1)
            val nextKey = if (data.isEmpty()) null else currentPage.plus(1)

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }
}