package com.example.movieapp.movieList.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.domain.repository.SearchRepository
import com.example.movieapp.movieList.util.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchApiPagingSource @Inject constructor(
    private val api: MovieApi,
    private val query: String
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1
        Log.d("Paging", "Loading page: $page")
        return try {
            val response = api.searchMovie(query, page, Constants.API_KEY)
            val nextKey = if (response.results.isEmpty() || page >= response.total_pages) {
                null
            } else {
                page + 1
            }
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
