package com.vavilon.compose.menu

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavMenuItem.Home,
        BottomNavMenuItem.Source,
        BottomNavMenuItem.Statistic,
        BottomNavMenuItem.Transaction,
    )
    BottomNavigation(
        backgroundColor = VavilonTheme.colors.backgroundUI,
        contentColor = VavilonTheme.colors.backgroundIcon,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconTint = if (isSelected) VavilonTheme.colors.primaryText
            else VavilonTheme.colors.primaryText.copy(0.4f)
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = iconTint,
                        modifier = Modifier.size(50.dp)
                    )
                },
                selectedContentColor = VavilonTheme.colors.primaryText,
                unselectedContentColor = VavilonTheme.colors.backgroundIcon.copy(0.4f),
                selected = item.route == currentRoute,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.popBackStack(navController.graph.startDestinationId, false)
                    }
                    if (currentRoute == item.route) {
                        Log.d("BottomNavigation", "Current Route: ${item.route}")
                    }
                }
            )
        }
    }
}

