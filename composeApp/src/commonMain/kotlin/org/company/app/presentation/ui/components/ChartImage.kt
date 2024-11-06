package org.company.app.presentation.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ChartImage(
    id: Int,
    modifier: Modifier,
    tintColor: Color,
) {
    val image: Resource<Painter> =
        asyncPainterResource("https://s3.coinmarketcap.com/generated/sparklines/web/7d/2781/$id.svg")
    KamelImage(
        { image }, contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillWidth,
        colorFilter = ColorFilter.tint(color = tintColor),
        onLoading = {
            LinearProgressIndicator(
                progress = { it },
            )
        }, animationSpec = tween(
            durationMillis = 300,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
}