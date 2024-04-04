package com.example.newsinshort.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.NewsApplication.Companion.TAG
import com.example.newsinshort.ui.components.EmptyStateComponent
import com.example.newsinshort.ui.components.Loader
import com.example.newsinshort.ui.components.NewsRowComponent
import com.example.newsinshort.ui.viewmodels.NewsViewModel
import com.example.utilities.ResourceState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
) {
    val newsResponse by newsViewModel.news.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    ) {
        val response = (newsResponse as? ResourceState.Success)?.data
        response?.articles?.size ?: 0
    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSize = PageSize.Fill,
        pageSpacing = 8.dp,
    ) { page: Int ->
        when (newsResponse) {
            is ResourceState.Loading -> {
                Loader()
            }

            is ResourceState.Success -> {
                val response = (newsResponse as ResourceState.Success).data
                Log.d(TAG + "Success", response.toString())
                if (response.articles.isNotEmpty() && page < response.articles.size) {
                    NewsRowComponent(page, response.articles.get(page))
                } else {
                    EmptyStateComponent()
                }
            }

            is ResourceState.Error -> {
                val error = newsResponse as ResourceState.Error

                Log.d(TAG + "Error", error.toString())
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
