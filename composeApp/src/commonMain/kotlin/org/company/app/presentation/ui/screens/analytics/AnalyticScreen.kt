package org.company.app.presentation.ui.screens.analytics

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class AnalyticScreen : Screen {
    @Composable
    override fun Content() {
        AnalyticsContent()
    }
}

@Composable
fun AnalyticsContent() {
    Text("AnalyticScreen Content")
}