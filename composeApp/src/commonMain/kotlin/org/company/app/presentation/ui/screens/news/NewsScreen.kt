package org.company.app.presentation.ui.screens.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.company.app.domain.model.news.Data
import org.company.app.domain.model.news.NewsList
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.ui.components.ErrorBox
import org.company.app.presentation.ui.components.LoadingBox
import org.company.app.presentation.viewmodel.MainViewModel
import org.koin.compose.koinInject

class NewsScreen : Screen {
    @Composable
    override fun Content() {
        NewsContent()
    }
}

@Composable
fun NewsContent(
    viewModel: MainViewModel = koinInject(),
) {
    var newsList by remember { mutableStateOf(emptyList<NewsList>()) }

    LaunchedEffect(Unit) {
        viewModel.getAllNews()
    }

    val newsState by viewModel.allNews.collectAsState()
    when (newsState) {
        is ResultState.ERROR -> {
            val error = (newsState as ResultState.ERROR).message
            ErrorBox(error)
        }
        ResultState.LOADING -> {
            LoadingBox()
        }
        is ResultState.SUCCESS -> {
            val response = (newsState as ResultState.SUCCESS).response
            newsList = response
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp)
    ) {
        items(newsList) { news ->

        }
    }
}

@Composable
fun NewsItemView(news: Data, onNewsClick: (Data) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNewsClick(news) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val image: Resource<Painter> = asyncPainterResource(news.imageurl)
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = news.body, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
    }
}