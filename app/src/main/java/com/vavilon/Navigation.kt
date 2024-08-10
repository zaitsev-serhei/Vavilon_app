package com.vavilon

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
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState

@Composable
fun Navigation(
    modifier: Modifier,
    sourceState: SourceState,
    transactionState: TransactionState,
    onEvent: (UserEvent)->Unit
) {
    val transactionEventHandler: (TransactionEvent) -> Unit = { event ->
        onEvent(UserEvent.TransactionEventWrapper(event))
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BottomNavMenuItem.Home.route) {
        composable(route = BottomNavMenuItem.Home.route) {
            HomeScreenView(
                sourceState = sourceState,
                transactionState = transactionState,
                navController = navController,
                onEvent = onEvent
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
            StatisticScreenView(navController = navController, modifier = modifier)
        }
        composable(route = BottomNavMenuItem.Transaction.route) {
            TransactionScreenView(navController = navController,
                transactionState = transactionState,
                onEvent = transactionEventHandler,
                modifier = modifier)
        }
    }
}