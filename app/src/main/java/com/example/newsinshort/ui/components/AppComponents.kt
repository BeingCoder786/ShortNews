package com.example.newsinshort.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsinshort.R
import com.example.newsinshort.data.entity.Article
import com.example.newsinshort.data.entity.NewsResponse
import com.example.newsinshort.ui.theme.Purple40

@Composable
fun Loader() {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp).padding(1.dp),
            color = Purple40,
        )
    }
}

@Composable
fun NormalTextComponent(textValue: String) {
    Text(
        text = textValue,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Purple40,
        ),
    )
}

@Composable
fun HeadingTextComponent(textValue: String) {
    Text(
        text = textValue,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium),
    )
}

@Composable
fun AuthorDetailsComponent(authourname: String = "", authourSource: String = "") {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
    ) {
        Text(text = authourname.orEmpty())
        Spacer(modifier = Modifier.weight(1f))
        Text(text = authourSource.orEmpty())
    }
}

@Composable
fun NewsList(response: NewsResponse) {
    LazyColumn {
        items(response.articles) { article ->
            NormalTextComponent(textValue = article.title ?: "NA")
        }
    }
}

@Composable
fun EmptyStateComponent() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Image(
            painterResource(id = R.drawable.no_data_7729),
            contentDescription = "No News as of now",
        )

        HeadingTextComponent(textValue = stringResource(R.string.no_news_as_of_now))
    }
}

@Composable
fun NewsRowComponent(page: Int, article: Article) {
    Column(
        modifier = Modifier.fillMaxSize().padding(2.dp).background(Color.White),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth().height(240.dp),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder_24),
            error = painterResource(id = R.drawable.placeholder_24),
        )

        Spacer(modifier = Modifier.size(20.dp))
        HeadingTextComponent(textValue = article.title.orEmpty())

        Spacer(modifier = Modifier.size(10.dp))
        NormalTextComponent(textValue = article.description.orEmpty())

        Spacer(modifier = Modifier.weight(1f))
        AuthorDetailsComponent(article.author.orEmpty(), article.source?.name.orEmpty())
    }
}

@Preview
@Composable
fun prevewio() {
    NewsRowComponent(1, Article("abhishek", "yooo", "sdfs", urlToImage = null))
}
