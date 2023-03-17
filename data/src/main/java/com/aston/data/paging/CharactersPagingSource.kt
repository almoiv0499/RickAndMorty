package com.aston.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.aston.data.database.ApplicationDatabase
import com.aston.data.model.character.CharacterInfoData
import com.aston.data.remote.CharactersService
import javax.inject.Inject

private const val BY_ONE = 1

class CharactersPagingSource @Inject constructor(
    private val database: ApplicationDatabase,
    private val service: CharactersService,
) : PagingSource<Int, CharacterInfoData>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterInfoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(BY_ONE) ?: anchorPage?.nextKey?.minus(BY_ONE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterInfoData> {
        val currentPage = params.key ?: BY_ONE

        return try {

            val response = service.fetchCharactersByPage(currentPage)
            val data = response.characterInfo

            database.withTransaction {
                database.charactersDao().insertCharacters(data)
            }

            val prevKey = if (currentPage == BY_ONE) null else currentPage.minus(BY_ONE)
            val nextKey = if (data.isEmpty()) null else currentPage.plus(BY_ONE)

            LoadResult.Page(
                data = data, prevKey = prevKey, nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }
}