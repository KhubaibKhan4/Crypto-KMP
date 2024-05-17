package org.company.app.presentation.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        ProfileContent()
    }
}

@Composable
fun ProfileContent() {
    Text("Profile Content")
}