package com.vavilon.utils

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object SourceScreen : Screen("source_screen")
    object TransactionScreen : Screen("transaction_screen")
    object StatisticScreen : Screen("statistic_screen")
    object AddNewSourceScreen : Screen("new_source_screen")
    object AddNewTransactionScreen : Screen("new_transaction_screen")
    object EditEntityScreen : Screen("edit_entity_screen")
}