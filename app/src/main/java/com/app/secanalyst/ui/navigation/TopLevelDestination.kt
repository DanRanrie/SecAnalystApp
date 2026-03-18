package com.app.secanalyst.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Explore
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val route: Route,
    val icon: ImageVector,
    val label: String
) {
    FAVORITES(Route.Favorites, Icons.Default.Build, "工具箱"),
    HOME(Route.Home, Icons.Filled.Explore, "首页"),
    PROFILE(Route.Profile, Icons.Default.AccountBox, "我的")
}
