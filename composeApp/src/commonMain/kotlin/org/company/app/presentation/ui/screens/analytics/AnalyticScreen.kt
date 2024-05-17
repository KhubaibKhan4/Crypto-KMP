package org.company.app.presentation.ui.screens.analytics

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.company.app.domain.model.crypto.Data
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.ui.components.ChartImage
import org.company.app.presentation.ui.components.CurrencyImage
import org.company.app.presentation.ui.components.ErrorBox
import org.company.app.presentation.ui.components.LoadingBox
import org.company.app.presentation.viewmodel.MainViewModel
import org.company.app.theme.LocalThemeIsDark
import org.koin.compose.koinInject
import kotlin.math.roundToInt

class AnalyticScreen : Screen {
    @Composable
    override fun Content() {
        AnalyticsContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsContent(viewModel: MainViewModel = koinInject()) {
    var listingData by remember { mutableStateOf<LatestListing?>(null) }
    LaunchedEffect(Unit) {
        viewModel.getLatestListing()
    }
    val state by viewModel.latestListing.collectAsState()
    when (state) {
        is ResultState.ERROR -> {
            val error = (state as ResultState.ERROR).message
            ErrorBox(error)
        }

        is ResultState.LOADING -> {
            LoadingBox()
        }

        is ResultState.SUCCESS -> {
            val response = (state as ResultState.SUCCESS).response
            listingData = response
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics") }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = it.calculateTopPadding(), start = 4.dp, end = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listingData?.data?.let { list ->
                    items(list) { data ->
                        CurrencyCard(data)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Top Movers",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val filteredData = listingData?.data
                    ?.filter { it.quote.uSD.percentChange24h >= 0 }
                    ?.sortedByDescending { it.quote.uSD.percentChange24h }
                filteredData?.let { list ->
                    items(list) { data ->
                        TopMoversContent(data)
                    }
                }
            }
        }
    }
}

@Composable
fun TopMoversContent(data: Data) {
    val isDark by LocalThemeIsDark.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red

    Card(
        modifier = Modifier
            .width(140.dp)
            .height(78.dp)
            .shadow(4.dp, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF333333) else Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${percentChange24h.roundToInt()}%",
                color = textColor24h,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${data.symbol} $" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        ChartImage(
            id = data.id,
            modifier = Modifier
                .width(55.dp)
                .align(Alignment.End),
            tintColor = textColor24h
        )
    }
}

@Composable
fun CurrencyCard(data: Data) {
    val isDark by LocalThemeIsDark.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    Card(
        modifier = Modifier.width(300.dp)
            .height(175.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF333333) else Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyImage(
                    id = data.id,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = data.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = data.symbol,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start,
                        color = Color.Gray
                    )
                }
            }
            ChartImage(
                id = data.id,
                modifier = Modifier.fillMaxWidth()
                    .height(80.dp),
                tintColor = textColor24h
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "${percentChange24h.roundToInt()}%",
                    color = textColor24h
                )
            }
        }
    }
}