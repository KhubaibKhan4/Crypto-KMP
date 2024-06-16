package org.company.app.presentation.ui.navigation.tab.news

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.company.app.presentation.ui.screens.news.NewsScreen

object News : Tab {
    @Composable
    override fun Content() {
        Navigator(NewsScreen())
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Newspaper)
            val title by remember { mutableStateOf("News") }
            val index: UShort = 2u
            return TabOptions(index, title, icon)
        }
}