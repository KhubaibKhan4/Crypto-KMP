package org.company.app.presentation.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import org.company.app.domain.model.crypto.Data
import org.company.app.presentation.ui.components.CurrencyImage
import org.company.app.theme.LocalThemeIsDark
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

class DetailScreen(
    private val data: Data,
) : Screen {
    @Composable
    override fun Content() {
        DetailContent(data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(data: Data) {
    var selectedPeriod by remember { mutableStateOf("1H") }
    val capList = remember { mutableListOf("1H", "1D", "1W", "1M", "3M", "6M", "1Y") }
    val isDark by LocalThemeIsDark.current
    val navigator = LocalNavigator.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val capMarket = data.quote.uSD.percentChange30d
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    val textColor1h = if (capMarket > 0) Color.Green else Color.Red
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "",
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "Menu Icon",
                        modifier = Modifier.clickable {
                            navigator?.pop()
                        }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications Icon"
                    )
                    Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = "Favourite Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .height(49.dp)
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(top = it.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyImage(
                    id = data.id,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = data.name,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val priceString = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$ ")
                    }
                    withStyle(
                        SpanStyle(
                            color = textColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(" USD")
                    }

                }
                Text(
                    text = priceString
                )
                Text(
                    text = "${percentChange24h.roundToInt()}%",
                    color = textColor24h,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyImage(
                    id = data.id,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                val latestCap = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${data.quote.uSD.fullyDilutedMarketCap}")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(" ${data.symbol}")
                    }

                }
                Text(
                    text = latestCap
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${capMarket.roundToInt()}%",
                    color = textColor1h,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TabRow(
                selectedTabIndex = capList.indexOf(selectedPeriod),
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(
                            tabPositions[capList.indexOf(
                                selectedPeriod
                            )]
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                capList.forEachIndexed { index, period ->
                    Tab(
                        selected = selectedPeriod == period,
                        onClick = { selectedPeriod = period },
                        text = { Text(text = period) },
                    )
                }
            }
            val listOfId = listOf(
                data.quote.uSD.percentChange1h,
                data.quote.uSD.percentChange24h,
                data.quote.uSD.percentChange7d,
                data.quote.uSD.percentChange30d,
            )

            val dataList = listOf(20.0, 44.15, 66.33, 35.0, 100.500)
            Column(
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (selectedPeriod) {
                    "1H" -> {
                        CryptoChart(data, "1H")
                    }

                    "1D" -> {
                        CryptoChart(data, "1D")

                    }

                    "1W" -> {
                        CryptoChart(data, "1W")
                    }

                    "1M" -> {
                        CryptoChart(data, "1M")
                    }

                    "3M" -> {
                        CryptoChart(data, "3M")
                    }

                    "6M" -> {
                        CryptoChart(data, "6M")
                    }

                    "1Y" -> {
                        CryptoChart(data, "1Y")
                    }

                }
            }
        }

    }
}

@Composable
fun CryptoChart(
    dataList: Data,
    selectedPeriod: String,
) {
    val relevantData = when (selectedPeriod) {
        "1H" -> listOf(
            dataList.quote.uSD.percentChange1h,
            dataList.quote.uSD.percentChange24h,
            dataList.quote.uSD.percentChange7d,
            dataList.quote.uSD.percentChange30d,
            dataList.quote.uSD.percentChange60d,
            dataList.quote.uSD.percentChange90d,
        )
        "1D" -> listOf(
            Random.nextDouble(0.0, 7.0),
            dataList.quote.uSD.percentChange24h,
            dataList.quote.uSD.percentChange7d,
            dataList.quote.uSD.percentChange30d,
            dataList.quote.uSD.percentChange60d,
            dataList.quote.uSD.percentChange90d,
        )
        "1W" -> listOf(
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            dataList.quote.uSD.percentChange7d,
            dataList.quote.uSD.percentChange30d,
            dataList.quote.uSD.percentChange60d,
            dataList.quote.uSD.percentChange90d,
        )
        "1M" -> listOf(
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            dataList.quote.uSD.percentChange30d,
            dataList.quote.uSD.percentChange60d,
            dataList.quote.uSD.percentChange90d,
        )
        "3M" -> listOf(
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            dataList.quote.uSD.percentChange60d,
            dataList.quote.uSD.percentChange90d,
        )
        "6M" -> listOf(
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            dataList.quote.uSD.percentChange90d,
        )
        "1Y" -> listOf(
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            Random.nextDouble(0.0, 7.0),
            dataList.quote.uSD.percentChange90d,
        )
        else -> emptyList()
    }

    val dataList1 = mutableListOf<Double>()
    dataList1.add(dataList.quote.uSD.percentChange1h)
    dataList1.add(dataList.quote.uSD.percentChange24h)
    dataList1.add(dataList.quote.uSD.percentChange7d)
    dataList1.add(dataList.quote.uSD.percentChange30d)
    dataList1.add(dataList.quote.uSD.percentChange60d)
    dataList1.add(dataList.quote.uSD.percentChange90d)
    println("DataList: $dataList1")
    val positiveDataList = relevantData.map { abs(it) }
    println("DataList: $positiveDataList")

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "Price",
            data = positiveDataList,
            lineColor = Color.Red,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
        )
    )

    Box(Modifier.wrapContentWidth()) {
        LineChart(
            modifier = Modifier.fillMaxWidth(),
            linesParameters = testLineParameters,
            isGrid = false,
            gridColor = Color.Blue,
            xAxisData = listOf(
                "2016",
                "2018",
                "2020",
                "2022",
                "2023",
                "2024"
            ),
            animateChart = true,
            showGridWithSpacer = true,
            legendPosition = LegendPosition.TOP,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 6,
            oneLineChart = false,
            gridOrientation = GridOrientation.VERTICAL
        )
    }
}


@Composable
fun BarChartSample() {

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            dataName = "Completed",
            data = listOf(0.6, 10.6, 80.0, 50.6, 44.0, 100.6, 10.0),
            barColor = Color(0xFF6C3428)
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(50.0, 30.6, 77.0, 69.6, 50.0, 30.6, 80.0),
            barColor = Color(0xFFBA704F),
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(100.0, 99.6, 60.0, 80.6, 10.0, 100.6, 55.99),
            barColor = Color(0xFFDFA878),
        ),
    )

    Box(Modifier.fillMaxSize()) {
        BarChart(
            chartParameters = testBarParameters,
            gridColor = Color.DarkGray,
            xAxisData = listOf("2016", "2017", "2018", "2019", "2020", "2021", "2022"),
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 15,
            barWidth = 20.dp
        )
    }
}

fun createDoubleList(
    percentChange1h: Double,
    percentChange24h: Double,
    percentChange7d: Double,
    percentChange30d: Double,
    percentChange60d: Double,
    percentChange1y: Double,
): List<Double> {
    return listOf(
        percentChange1h,
        percentChange24h,
        percentChange7d,
        percentChange30d,
        percentChange60d,
        percentChange1y
    )
}