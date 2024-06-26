package org.company.app.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import org.company.app.domain.model.crypto.Data
import kotlin.math.abs
import kotlin.random.Random

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

    var isPieCharEnabled by remember { mutableStateOf(false) }
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

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isPieCharEnabled) {
            LineChart(
                modifier = Modifier.fillMaxWidth()
                    .height(270.dp),
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
        } else {
            val testPieChartData: List<PieChartData> = listOf(
                PieChartData(
                    partName = "1H",
                    data = 40.32,
                    color = Color(0xFF22A699),
                ),
                PieChartData(
                    partName = "24H",
                    data = 65.02,
                    color = Color(0xFFF2BE22),
                ),
                PieChartData(
                    partName = "7D",
                    data = 42.32,
                    color = Color(0xFFF29727),
                ),
                PieChartData(
                    partName = "1M",
                    data = 15.32,
                    color = Color(0xFFF24C3D),
                ),
                PieChartData(
                    partName = "2M",
                    data = 90.2,
                    color = Color(0xFFF24C3D),
                ),
                PieChartData(
                    partName = "3M",
                    data = 55.4,
                    color = Color(0xFFF24C3D),
                ),
            )

            PieChart(
                modifier = Modifier.fillMaxWidth()
                    .height(270.dp),
                pieChartData = testPieChartData,
                ratioLineColor = Color.LightGray,
                textRatioStyle = TextStyle(color = Color.Gray),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isPieCharEnabled,
                onCheckedChange = {
                    isPieCharEnabled = it
                }
            )
            Text(
                text = dataList.symbol + " Pie Chart",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}