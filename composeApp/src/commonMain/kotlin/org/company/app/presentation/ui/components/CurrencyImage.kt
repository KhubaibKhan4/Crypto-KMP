package org.company.app.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun CurrencyImage(
    id: Int,
    modifier: Modifier,
) {
    AsyncImage(
        model = "https://s2.coinmarketcap.com/static/img/coins/64x64/$id.png",
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds,
    )
}