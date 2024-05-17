package org.company.app.presentation.ui.navigation.tab.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.company.app.presentation.ui.screens.home.HomeScreen

object Home : Tab {
    @Composable
    override fun Content() {
        Navigator(HomeScreen())
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            val title by remember { mutableStateOf("Home") }
            val index: UShort = 0u
            return TabOptions(index, title, icon)
        }
}