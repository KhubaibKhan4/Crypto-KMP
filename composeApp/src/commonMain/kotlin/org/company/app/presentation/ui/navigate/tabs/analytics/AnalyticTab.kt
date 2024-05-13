package org.company.app.presentation.ui.navigate.tabs.analytics

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.company.app.presentation.ui.screens.home.HomeScreen

object AnalyticTab : Tab {
    @Composable
    override fun Content() {
        Text("Analytics")
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Analytics"
            val icon = rememberVectorPainter(Icons.Default.Analytics)
            val index: UShort = 1u
            return TabOptions(index, title, icon)
        }
}