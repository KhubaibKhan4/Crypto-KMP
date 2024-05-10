package org.company.app.presentation.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.company.app.domain.model.crypto.Data
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.ui.components.ErrorBox
import org.company.app.presentation.ui.components.LoadingBox
import org.company.app.presentation.viewmodel.MainViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    viewModel: MainViewModel = koinInject(),
) {
    var listingData by remember { mutableStateOf<LatestListing?>(null) }
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listingData?.data?.let { dataList ->
            if (dataList.isNotEmpty()) {
                CryptoList(dataList = dataList)
            } else {
                Text(text = "No data available.")
            }
        }
    }
}

@Composable
fun CryptoList(dataList: List<Data>) {
    LazyColumn {
        items(dataList) { data ->
            CryptoItem(data = data)
            HorizontalDivider()
        }
    }
}

@Composable
fun CryptoItem(data: Data) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Name: ${data.name}")
            Text(text = "Symbol: ${data.symbol}")
            Text(text = "Rank: ${data.cmcRank}")
            Text(text = "Price: $${data.quote.uSD.price}")
        }
    }
}