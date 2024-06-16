package org.company.app.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.company.app.domain.model.news.Data

class NewsDetailScreen(private val news: Data) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
       Scaffold(
           modifier = Modifier.fillMaxWidth(),
           topBar = {
               TopAppBar(
                   title = {Text(text = news.title.take(15))},
                   navigationIcon = {
                       Icon(
                           imageVector = Icons.Default.ArrowBackIosNew,
                           contentDescription = "Arrow Back",
                           modifier = Modifier.clickable {
                               navigator?.pop()
                           }
                       )
                   }
               )
           }
       ){
           Column(
               modifier = Modifier.fillMaxWidth()
                   .padding(top = it.calculateTopPadding()),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.spacedBy(12.dp)
           ) {
               NewsDetailContent(news)
           }
       }
    }
}

@Composable
fun NewsDetailContent(news: Data) {
    Column(modifier = Modifier.padding(16.dp)) {
        val image: Resource<Painter> = asyncPainterResource(news.imageurl)
        KamelImage(
            resource = image,
            contentDescription = null,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = news.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = news.body, style = MaterialTheme.typography.bodyLarge)
    }
}
