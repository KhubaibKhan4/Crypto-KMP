package org.company.app.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CurrencyImage(
    id: Int,
    modifier: Modifier,
) {
    val image: Resource<Painter> = asyncPainterResource("https://s2.coinmarketcap.com/static/img/coins/64x64/$id.png")
    KamelImage(
        { image }, contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds,
        onLoading = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(progress = {it})
            }
        }
    )
}