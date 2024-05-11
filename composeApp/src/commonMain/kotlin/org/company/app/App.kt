package org.company.app

import androidx.compose.runtime.Composable
import org.company.app.presentation.ui.screens.home.HomeScreen
import org.company.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    HomeScreen()
}

internal expect fun openUrl(url: String?)