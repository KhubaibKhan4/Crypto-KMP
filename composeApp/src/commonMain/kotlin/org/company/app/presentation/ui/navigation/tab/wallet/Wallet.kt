package org.company.app.presentation.ui.navigation.tab.wallet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.company.app.presentation.ui.screens.home.HomeScreen

object Wallet : Tab {
    @Composable
    override fun Content() {
        Text("Wallet")
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Wallet)
            val title by remember { mutableStateOf("Wallet") }
            val index: UShort = 2u
            return TabOptions(index, title, icon)
        }
}