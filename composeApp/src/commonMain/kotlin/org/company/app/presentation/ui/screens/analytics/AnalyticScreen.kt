package org.company.app.presentation.ui.screens.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import org.company.app.domain.model.crypto.Data
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.ui.components.ChartImage
import org.company.app.presentation.ui.components.CurrencyImage
import org.company.app.presentation.ui.components.ErrorBox
import org.company.app.presentation.ui.components.LoadingBox
import org.company.app.presentation.ui.screens.detail.DetailScreen
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 4.dp, end = 4.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            LazyRow {
                listingData?.data?.let { data ->
                    items(data) { list ->
                        SummarySection(list)
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
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
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Top Losing Currencies",
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
                val losingData = listingData?.data
                    ?.filter { it.quote.uSD.percentChange24h < 0 }
                    ?.sortedBy { it.quote.uSD.percentChange24h }
                losingData?.let { list ->
                    items(list) { data ->
                        TopLosingContent(data)
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listingData?.data?.let { list ->
                    items(list) { data ->
                        CurrencyCard(data)
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Market Trends",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                val randomCurrency = listingData?.data?.random()
                randomCurrency?.let { data ->
                    val textColor =
                        if (data.quote.uSD.percentChange24h > 0) Color.Green else Color.Red

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "${data.name} (${data.symbol})",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$${(data.quote.uSD.price * 100).roundToInt() / 100.0}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (data.quote.uSD.percentChange24h > 0) "▲" else "▼",
                                fontSize = 20.sp,
                                color = textColor,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${data.quote.uSD.percentChange24h.roundToInt()}% in 24h",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = textColor
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                        )
                        ChartImage(
                            id = data.id,
                            modifier = Modifier
                                .fillMaxWidth(),
                            tintColor = textColor
                        )
                    }
                } ?: run {
                    Text(
                        text = "No data available",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun SummarySection(data: Data) {
    val isDark by LocalThemeIsDark.current
    val navigator = LocalNavigator.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable {
                navigator?.push(DetailScreen(data))
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF1A1A1A) else Color(0xFFF0F0F0)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Market Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Total Market Cap",
                        fontSize = 14.sp,
                        color = if (isDark) Color.White else Color.Gray
                    )
                    Text(
                        text = "$${data?.quote?.uSD?.fullyDilutedMarketCap ?: "N/A"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDark) Color.White else Color.Black
                    )
                }
                Column {
                    Text(
                        text = "24h Volume",
                        fontSize = 14.sp,
                        color = if (isDark) Color.White else Color.Gray
                    )
                    Text(
                        text = "$${data?.quote?.uSD?.percentChange24h ?: "N/A"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDark) Color.White else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun TopLosingContent(data: Data) {
    val isDark by LocalThemeIsDark.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = Color.Red
    val navigator = LocalNavigator.current

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(90.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable {
                navigator?.push(DetailScreen(data))
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF1A1A1A) else Color(0xFFFFCDD2)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 2.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Loss",
                    tint = textColor24h,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${percentChange24h.roundToInt()}%",
                    color = textColor24h,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${data.symbol} $" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data.name,
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun TopMoversContent(data: Data) {
    val navigator = LocalNavigator.current
    val isDark by LocalThemeIsDark.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(90.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable {
                navigator?.push(DetailScreen(data))
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF1A1A1A) else Color(0xFFE8F5E9)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end= 10.dp, top= 2.dp, bottom = 2.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Gain",
                    tint = textColor24h,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${percentChange24h.roundToInt()}%",
                    color = textColor24h,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurrencyImage(
                        id = data.id,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${data.symbol} $" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = data.name,
                            fontSize = 12.sp,
                            color = textColor.copy(alpha = 0.7f)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CurrencyCard(data: Data) {
    val isDark by LocalThemeIsDark.current
    val navigator = LocalNavigator.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(175.dp)
            .clickable {
                navigator?.push(DetailScreen(data))
            },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF333333) else Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                modifier = Modifier
                    .fillMaxWidth()
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