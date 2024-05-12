package org.company.app.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import org.company.app.domain.model.crypto.Data
import org.company.app.presentation.ui.screens.detail.DetailScreen
import org.company.app.theme.LocalThemeIsDark
import kotlin.math.roundToInt

@Composable
fun CryptoList(
    dataList: List<Data>,
    coinsText: String,
    viewText: String,
    largeCapColor: Color = Color.Black,
    isCapIconEnabled: Boolean = false,
) {
    val isDark by LocalThemeIsDark.current
    val viewALlColor =
        if (viewText.contains("View All")) if (isDark) Color.White else Color.Black else largeCapColor
    val textColor = if (isDark) Color.White else Color.Black
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 3.dp, end = 3.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = coinsText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = viewText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = viewALlColor
                )
                AnimatedVisibility(isCapIconEnabled) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = largeCapColor
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            dataList.forEach { data ->
                CryptoItem(data = data)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun CryptoItem(data: Data) {
    val navigator = LocalNavigator.current
    val isDark by LocalThemeIsDark.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navigator?.push(DetailScreen(data))
            },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.cmcRank.toString(),
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                color = textColor
            )
            Spacer(modifier = Modifier.width(50.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyImage(
                    id = data.id,
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = data.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Text(
                        text = data.symbol,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            ChartImage(
                id = data.id,
                modifier = Modifier.fillMaxWidth(0.40f),
                tintColor = textColor24h
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${percentChange24h.roundToInt()}%",
                    color = textColor24h
                )
            }
        }
    }
}