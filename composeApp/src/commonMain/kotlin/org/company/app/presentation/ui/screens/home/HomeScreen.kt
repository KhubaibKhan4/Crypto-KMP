package org.company.app.presentation.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.company.app.domain.usecase.ResultState
import org.company.app.presentation.viewmodel.MainViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    viewModel: MainViewModel = koinInject()
) {
    LaunchedEffect(Unit){
        viewModel.getLatestListing()
    }
    val latestState by viewModel.latestListing.collectAsState()
    when(latestState){
        is ResultState.ERROR -> {
            val error = (latestState as ResultState.ERROR).message
        }
        is ResultState.LOADING -> {

        }
        is ResultState.SUCCESS -> {
            val data = (latestState as ResultState.SUCCESS).response
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}