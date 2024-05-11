package org.company.app.presentation.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
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
    tintColor: Color
) {
    val image: Resource<Painter> =
        asyncPainterResource("https://s3.coinmarketcap.com/generated/sparklines/web/7d/2781/$id.svg")
    KamelImage(
        resource = image,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds,
        animationSpec = tween(
            durationMillis = 1500,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        ),
        colorFilter = ColorFilter.tint(color = tintColor)
    )
}