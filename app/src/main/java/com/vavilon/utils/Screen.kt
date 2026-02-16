package com.vavilon.utils

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object SourceScreen : Screen("source_screen")
    object TransactionScreen : Screen("transaction_screen")
    object StatisticScreen : Screen("statistic_screen")
    object Plan: Screen("plan")
    object AddNewSourceScreen : Screen("new_source_screen")
    object AddNewTransactionScreen : Screen("new_transaction_screen")
    object EditPlanScreen : Screen("plan_edit_screen")
}