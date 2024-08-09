package com.example.movieapp.movieList.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.movieList.data.remote.api.MovieApi
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.util.Constants
import java.io.IOException
import javax.inject.Inject

class PopularApiPagingSource @Inject constructor(
    private val api: MovieApi,
    private val language: String
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1
        Log.d("Paging", "Loading page: $page")
        return try {
            val popularResponse = api.popularMovie(page, language, Constants.API_KEY)
            LoadResult.Page(
                data = popularResponse.results,
                prevKey = if (page == 1) null else (page - 1),
                nextKey = if (page == popularResponse.total_pages) null else (page + 1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
