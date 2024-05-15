package org.company.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.company.app.presentation.ui.screens.home.HomeScreen
import org.company.app.theme.AppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun App() = AppTheme {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    Navigator(HomeScreen())
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context)
        .crossfade(true)
        .logger(DebugLogger())
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

internal expect fun openUrl(url: String?)