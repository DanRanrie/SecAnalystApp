package com.app.secanalyst.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.secanalyst.ui.navigation.SecAnalystNavHost
import com.app.secanalyst.ui.navigation.TopLevelDestination
import com.app.secanalyst.ui.toast.LocalToastState
import com.app.secanalyst.ui.toast.ToastState
import com.app.secanalyst.ui.toast.ToastUI
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun SecAnalystApp() {
    val toastState = remember { ToastState() }
    CompositionLocalProvider(LocalToastState provides toastState) {
        Box {
            SecAnalystAppContent()
            ToastUI(state = toastState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
private fun SecAnalystAppContent() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach { destination ->
                val selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(destination.route::class)
                } == true
                
                item(
                    selected = selected,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(text = destination.label) },
                    alwaysShowLabel = false
                )
            }
        }
    ) {
        SecAnalystNavHost(navController = navController)
    }
}
