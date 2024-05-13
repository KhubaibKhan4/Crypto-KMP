package org.company.app.presentation.ui.navigate.tabs.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.company.app.presentation.ui.screens.home.HomeScreen

object CategoriesTab : Tab {
    @Composable
    override fun Content() {
        Text("Categories")
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Categories"
            val icon = rememberVectorPainter(Icons.Default.Category)
            val index: UShort = 2u
            return TabOptions(index, title, icon)
        }
}