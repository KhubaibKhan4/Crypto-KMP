package org.company.app

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.company.app.presentation.ui.navigate.rails.items.NavigationItem
import org.company.app.presentation.ui.navigate.rails.navbar.NavigationSideBar
import org.company.app.presentation.ui.navigate.tabs.analytics.AnalyticTab
import org.company.app.presentation.ui.navigate.tabs.categories.CategoriesTab
import org.company.app.presentation.ui.navigate.tabs.home.HomeTab
import org.company.app.presentation.ui.navigate.tabs.profile.ProfileTab
import org.company.app.theme.AppTheme
import org.company.app.theme.LocalThemeIsDark

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun App() = AppTheme {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    AppContent()
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context)
        .crossfade(true)
        .logger(DebugLogger())
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppContent() {
    val items = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavigationItem(
            title = "Analytics",
            selectedIcon = Icons.Filled.Analytics,
            unselectedIcon = Icons.Outlined.Analytics,
            hasNews = true,
        ),
        NavigationItem(
            title = "Categories",
            selectedIcon = Icons.Filled.Category,
            unselectedIcon = Icons.Outlined.Category,
            hasNews = false,
        ),
        NavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.AccountBox,
            unselectedIcon = Icons.Outlined.AccountBox,
            hasNews = false,
        ),
    )
    val windowClass = calculateWindowSizeClass()
    val showNavigationRail = windowClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    TabNavigator(HomeTab) { tabNavigator ->
        Scaffold(bottomBar = {
            if (!showNavigationRail) {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.ime),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = contentColorFor(Color.Red),
                    elevation = 8.dp
                ) {
                    TabItem(HomeTab)
                    TabItem(AnalyticTab)
                    TabItem(CategoriesTab)
                    TabItem(ProfileTab)
                }
            }
        }) {
            Column(
                modifier = Modifier.fillMaxSize().padding(
                    bottom = it.calculateBottomPadding(),
                    start = if (showNavigationRail) 80.dp else 0.dp
                )
            ) {
                CurrentTab()
            }
        }
    }
    if (showNavigationRail) {
        NavigationSideBar(
            items = items,
            selectedItemIndex = selectedItemIndex,
            onNavigate = {
                selectedItemIndex = it
            }
        )

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(start = 80.dp)
        ) {
            when (selectedItemIndex) {
                0 -> {

                }

                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        TabNavigator(AnalyticTab)
                    }
                }

                2 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        TabNavigator(CategoriesTab)
                    }
                }

                3 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        TabNavigator(ProfileTab)
                    }
                }
            }
        }

    }
}

@Composable
fun RowScope.TabItem(tab: Tab) {
    val isDark by LocalThemeIsDark.current
    val tabNavigator = LocalTabNavigator.current
    BottomNavigationItem(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            .height(58.dp).clip(RoundedCornerShape(16.dp)),
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter,
                    contentDescription = tab.options.title,
                    tint = if (tabNavigator.current == tab) Color.Red else if (isDark) Color.White else Color.Black
                )
            }
        },
        label = {
            tab.options.title.let { title ->
                Text(
                    title,
                    fontSize = 12.sp,
                    color = if (tabNavigator.current == tab) Color.Red else if (isDark) Color.White else Color.Black
                )
            }
        },
        enabled = true,
        alwaysShowLabel = true,
        interactionSource = MutableInteractionSource(),
        selectedContentColor = Color.Red,
        unselectedContentColor = Color.Black
    )
}

internal expect fun openUrl(url: String?)