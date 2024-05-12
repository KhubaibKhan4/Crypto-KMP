package org.company.app.presentation.ui.screens.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class DetailScreen : Screen {
    @Composable
    override fun Content() {
        DetailContent()
    }
}

@Composable
fun DetailContent() {
    Text("Detail Content")
}