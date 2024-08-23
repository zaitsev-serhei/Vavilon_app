package com.vavilon.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vavilon.compose.chart.BarChartTransaction
import com.vavilon.compose.menu.BottomNavigation
import com.vavilon.compose.transaction.VerticalTransactionListView
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.TransactionState
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.ui.theme.DarkBlue
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import java.util.Date

@Composable
fun TransactionScreenView(
    transactionState: TransactionState,
    onEvent: (UserEvent) -> Unit,
    navController: NavController,
    modifier: Modifier
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue),
        bottomBar = {
            BottomNavigation(
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .background(VavilonTheme.colors.backgroundUI)
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Transactions This Month", style = Typography.h1)
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(10.dp)
            ) {
                BarChartTransaction(transactionState = transactionState)
            }
            Spacer(modifier = Modifier.height(10.dp))
            VerticalTransactionListView(
                transactionState = transactionState,
                userEvent = onEvent
            )
        }
    }
}

@Preview(heightDp = 800, widthDp = 600)
@Composable
private fun TransactionTransactionScreenPreview() {
    VavilonTheme {
        val navController = rememberNavController()
        TransactionScreenView(navController = navController, transactionState = TransactionState(
            listOf(
                Transaction(1005.0, "Income", Date()),
                Transaction(1805.0, "Food", Date()),
                Transaction(1005.0, "Rent", Date()),
                Transaction(1005.0, "Income", Date())
            )
        ), onEvent = {}, modifier = Modifier
        )
    }

}