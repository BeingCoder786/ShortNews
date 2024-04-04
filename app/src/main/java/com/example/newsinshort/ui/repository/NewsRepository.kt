package com.example.newsinshort.ui.repository

import com.example.newsinshort.data.datasource.NewsDataSource
import com.example.newsinshort.data.entity.NewsResponse
import com.example.utilities.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Newsapi.org - public library API
class NewsRepository @Inject constructor(
    private val newsDataSource: NewsDataSource,
) {

    // suspend fun getNewsHeadline(country:String): Response<NewsResponse>{
    //    return newsDataSource.getNewsHeadline(country)
    // }

    suspend fun getNewsHeadline(country: String): Flow<ResourceState<NewsResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = newsDataSource.getNewsHeadline(country)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error"))
            }
        }.catch { e ->
            emit(ResourceState.Error(e?.localizedMessage ?: " error something not in our side"))
        }
    }
}
