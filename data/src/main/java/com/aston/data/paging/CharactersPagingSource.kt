package com.aston.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aston.data.database.datasource.CharacterDataSource
import com.aston.data.model.character.CharacterInfoData
import com.aston.data.remote.CharactersService

private const val BY_ONE = 1

class CharactersPagingSource(
    private val characterName: String,
    private val characterStatus: String,
    private val characterSpecies: String,
    private val characterGender: String,
    private val dataSource: CharacterDataSource,
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

            val response = service.fetchCharactersByPage(
                currentPage, characterName, characterStatus, characterSpecies, characterGender
            )
            val data = response.characterInfo

            dataSource.insertCharacters(data)

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