package org.company.app.presentation.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.company.app.domain.model.crypto.Data
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.ui.components.CryptoList
import org.company.app.presentation.ui.components.ErrorBox
import org.company.app.presentation.ui.components.LoadingBox
import org.company.app.presentation.ui.components.SuggestionMessage
import org.company.app.presentation.viewmodel.MainViewModel
import org.company.app.theme.LocalThemeIsDark
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
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
                    if (queryText.isEmpty()){
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
                            Spacer(modifier = Modifier.height(8.dp))
                            SuggestionMessage()
                            Spacer(modifier = Modifier.height(8.dp))
                            CryptoList(
                                dataList = dataList,
                                coinsText = "All Coins",
                                viewText = "View All"
                            )
                        } else {
                            Text(text = "No data available.")
                        }
                    }else{
                        val filteredList = dataList.filter { it.name.contains(queryText, ignoreCase = true) }
                        if (filteredList.isNotEmpty()) {
                            CryptoList(
                                dataList = filteredList,
                                coinsText = "Search Results",
                                viewText = "View All"
                            )
                        } else {
                            Text(text = "No matching results.")
                        }
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