package org.company.app.presentation.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel = koinInject(),
) {
    var isDark by LocalThemeIsDark.current
    var listingData by remember { mutableStateOf<LatestListing?>(null) }
    var queryText by remember { mutableStateOf("") }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() {
        refreshScope.launch {
            viewModel.getLatestListing()
            delay(1500)
            refreshing = false
        }
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)
    LaunchedEffect(Unit) {
        viewModel.getLatestListing()
    }
    val latestState by viewModel.latestListing.collectAsState()
    when (latestState) {
        is ResultState.ERROR -> {
            val error = (latestState as ResultState.ERROR).message
            ErrorBox(error)
        }

        is ResultState.LOADING -> {
            LoadingBox()
        }

        is ResultState.SUCCESS -> {
            val data = (latestState as ResultState.SUCCESS).response
            listingData = data
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Track Coins",
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.WbSunny,
                        contentDescription = "Menu Icon",
                        modifier = Modifier.clickable {
                            isDark = !isDark
                        }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Menu Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .height(49.dp)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pullRefresh(state = refreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .padding(top = it.calculateTopPadding())
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = queryText,
                    onValueChange = {
                        queryText = it
                    },
                    placeholder = { Text(text = "Search Coins") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = if (isDark) Color.White else Color.Black,
                        unfocusedTextColor = if (isDark) Color.White else Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(40.dp)
                )
                listingData?.data?.let { dataList ->
                    if (dataList.isNotEmpty()) {
                        val combinedFilteredList = mutableListOf<Data>()
                        if (dataList.size > 0) {
                            combinedFilteredList.add(dataList[0])
                        }
                        if (dataList.size > 12) {
                            combinedFilteredList.add(dataList[12])
                        }

                        CryptoList(
                            dataList = combinedFilteredList,
                            coinsText = "Favourite",
                            viewText = "Large Cap",
                            largeCapColor = Color(0xFFc127d9),
                            isCapIconEnabled = true
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        CryptoList(
                            dataList = dataList,
                            coinsText = "All Coins",
                            viewText = "View All"
                        )
                    } else {
                        Text(text = "No data available.")
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun CryptoList(
    dataList: List<Data>,
    coinsText: String,
    viewText: String,
    largeCapColor: Color = Color.Black,
    isCapIconEnabled: Boolean = false,
) {
    val isDark by LocalThemeIsDark.current
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
                    color = largeCapColor
                )
                AnimatedVisibility (isCapIconEnabled){
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
    val isDark by LocalThemeIsDark.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                modifier = Modifier.fillMaxWidth(0.3f),
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