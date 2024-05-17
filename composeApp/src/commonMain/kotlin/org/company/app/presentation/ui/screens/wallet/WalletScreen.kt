package org.company.app.presentation.ui.screens.wallet

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class WalletScreen : Screen {
    @Composable
    override fun Content() {
        WalletContent()
    }
}

@Composable
fun WalletContent() {
    Text("Wallet Content")
}