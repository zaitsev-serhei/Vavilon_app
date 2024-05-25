package com.vavilon.compose.menu

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import com.vavilon.ui.theme.Bronze
import com.vavilon.ui.theme.DarkBlue
import com.vavilon.ui.theme.Gold

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavMenuItem.Home,
        BottomNavMenuItem.Source,
        BottomNavMenuItem.Statistic,
        BottomNavMenuItem.Transaction,
    )
    BottomNavigation(
        backgroundColor = DarkBlue,
        contentColor = Bronze,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconTint = if (isSelected) Gold else Gold.copy(0.4f)
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon),
                    contentDescription = item.label,
                    tint = iconTint,
                    modifier = Modifier.size(50.dp))
                       },
                selectedContentColor = Gold,
                unselectedContentColor = Bronze.copy(0.4f),
                selected = item.route == currentRoute  ,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            if (currentRoute == item.route) {
                Log.d("BottomNavigation", "Current Route: ${item.route}")
            }
        }

    }
}


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavMenuItem.Home.route) {
        composable(BottomNavMenuItem.Home.route) { /* Home screen content */ }
        composable(BottomNavMenuItem.Source.route) { /* Source screen content */ }
        composable(BottomNavMenuItem.Statistic.route) { /* Statistic screen content */ }
        composable(BottomNavMenuItem.Transaction.route) { /* Transaction screen content */ }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomMenuPreview() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

    }
}