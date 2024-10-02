package org.company.app.presentation.ui.screens.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import org.company.app.domain.model.news.Data
import org.company.app.domain.model.news.NewsList
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.ui.components.ErrorBox
import org.company.app.presentation.ui.components.LoadingBox
import org.company.app.presentation.ui.components.NewsDetailScreen
import org.company.app.presentation.ui.components.PromotionCardWithPager
import org.company.app.presentation.viewmodel.MainViewModel
import org.company.app.utils.formatTimestamp
import org.koin.compose.koinInject
import kotlin.math.min

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
    var newsList by remember { mutableStateOf<NewsList?>(null) }

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

    Column(modifier = Modifier.fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBars)) {
        HeaderSection(newsList?.data)
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            newsList?.data?.let { listData ->
                items(listData) {
                    NewsItemView(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderSection(newsList: List<Data>?, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        if (!newsList.isNullOrEmpty()) {
            val itemsToDisplay = min(8, newsList.size)
            val shuffledNewsList = remember { newsList.shuffled() }

            if (itemsToDisplay > 0) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    PromotionCardWithPager(shuffledNewsList)
                }
            }
        } else {
            Text(
                text = "No news available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        QuickFilters()
    }
}

@Composable
fun QuickFilters(
    viewModel: MainViewModel = koinInject()
) {
    LaunchedEffect(Unit){
        viewModel.getNewsCategories()
    }
    val newsCategories by viewModel.newsCategories.collectAsState()

    when(newsCategories){
        is ResultState.ERROR -> {
            val error = (newsCategories as ResultState.ERROR).message
            ErrorBox(error)
        }
        ResultState.LOADING -> {
            LoadingBox()
        }
        is ResultState.SUCCESS -> {
            val response = (newsCategories as ResultState.SUCCESS).response
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(response) { filter ->
                    Chip(text = filter.categoryName)
                }
            }
        }
    }

}


@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        modifier = Modifier.clickable {  }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun NewsItemView(news: Data) {
    val navigator = LocalNavigator.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navigator?.push(NewsDetailScreen(news)) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val image: Resource<Painter> = asyncPainterResource(news.imageurl)
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 100f
                        )
                    )
            ) {
                KamelImage(
                    resource = image,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = news.sourceInfo.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = news.sourceInfo.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = " Published: ${formatTimestamp(news.publishedOn.toLong())}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}