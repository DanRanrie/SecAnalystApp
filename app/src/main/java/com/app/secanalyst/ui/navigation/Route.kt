package com.app.secanalyst.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Favorites : Route
    @Serializable
    data object Home : Route
    @Serializable
    data object Profile : Route
    @Serializable
    data object IpDetail : Route
}
