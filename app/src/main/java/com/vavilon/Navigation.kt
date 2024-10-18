package com.vavilon

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vavilon.compose.HomeScreenView
import com.vavilon.compose.SourceScreenView
import com.vavilon.compose.StatisticScreenView
import com.vavilon.compose.TransactionScreenView
import com.vavilon.compose.menu.BottomNavMenuItem
import com.vavilon.compose.source.AddNewSourceScreen
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.utils.Screen

@Composable
fun Navigation(
    modifier: Modifier,
    sourceState: SourceState,
    transactionState: TransactionState,
    onEvent: (UserEvent) -> Unit
) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BottomNavMenuItem.Home.route) {
        composable(route = BottomNavMenuItem.Home.route) {
            HomeScreenView(
                sourceState = sourceState,
                transactionState = transactionState,
                navController = navController,
                onEvent = onEvent,
                onAddSource = { navController.navigate(route = Screen.AddNewEntityScreen.route) },
                onAddTransaction = { navController.navigate(route = Screen.AddNewEntityScreen.route) },
                onSaved = {}
            )
        }
        composable(route = BottomNavMenuItem.Source.route) {
            SourceScreenView(
                modifier = modifier,
                state = sourceState,
                navController = navController,
                onEvent = onEvent
            )
        }
        composable(route = BottomNavMenuItem.Statistic.route) {
            StatisticScreenView(
                navController = navController,
                modifier = modifier
            )
        }
        composable(route = BottomNavMenuItem.Transaction.route) {
            TransactionScreenView(
                navController = navController,
                transactionState = transactionState,
                onEvent = onEvent,
                modifier = modifier
            )
        }
        composable(route = Screen.AddNewEntityScreen.route) {
            Log.d("Source to save", "New Source: ${sourceState.isAddingNewSource}")
            AddNewSourceScreen(
                state = sourceState.copy(isAddingNewSource = true),
                onEvent = onEvent,
                onSaved = { navController.navigate(route = Screen.HomeScreen.route) })
        }
    }
}