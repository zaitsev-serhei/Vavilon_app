package com.vavilon.compose.menu

import com.vavilon.R
import com.vavilon.utils.Screen

sealed class BottomNavMenuItem(var route: String, var icon: Int, var label: String) {
    object Home : BottomNavMenuItem(Screen.HomeScreen.route,
        R.drawable.ic_home,
        "Home")
    object Source : BottomNavMenuItem(Screen.SourceScreen.route,
        R.drawable.ic_source,
        "Sources")
    object Statistic : BottomNavMenuItem(Screen.StatisticScreen.route,
            R.drawable.ic_statistics,
            "Statistic")

    object Transaction : BottomNavMenuItem(Screen.TransactionScreen.route,
        R.drawable.ic_transactions,
        "Transaction")
}