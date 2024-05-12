package org.company.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.company.app.presentation.ui.screens.home.HomeContent
import org.company.app.presentation.ui.screens.home.HomeScreen
import org.company.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
   Navigator(HomeScreen())
}

internal expect fun openUrl(url: String?)