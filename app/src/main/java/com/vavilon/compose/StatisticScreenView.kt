package com.vavilon.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vavilon.compose.menu.BottomNavigation
import com.vavilon.model.dataHandlers.ExchangeRate
import com.vavilon.model.events.ExchangeRatesEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.ExchangeRatesState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import java.util.Date

@Composable
fun StatisticScreenView(
    ratesState: ExchangeRatesState,
    navController: NavController,
    modifier: Modifier,
    onEvent: (UserEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(VavilonTheme.colors.backgroundUI),
        bottomBar = {
            BottomNavigation(
                navController = navController
            )
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Currency Rates (USD)",
                maxLines = 1,
                style = Typography.body1
            )
            ratesState.exchangeRatesList.forEachIndexed { index, exchangeRate ->
                Row(modifier.fillMaxWidth()
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = exchangeRate.relatedCurrencyCode,
                        style = Typography.body1
                    )
                    Text(
                        text = exchangeRate.rate.toString(),
                        style = Typography.body1
                    )
                }
            }
            Button(onClick = {
                onEvent(UserEvent.ExchangeRatesEventWrapper(ExchangeRatesEvent.RefreshCurrencies))
            })
            {
                Text(text = "Refresh")
            }
        }
    }
}

@Preview
@Composable
private fun preview1() {
    VavilonTheme {
        val navController = rememberNavController()
        StatisticScreenView(
            ratesState = ExchangeRatesState(
                listOf(
                    ExchangeRate(
                        "USD",
                        "",
                        "UKR",
                        "",
                        1.02,
                        Date(),
                        true
                    ),
                    ExchangeRate(
                        "USD",
                        "",
                        "AUD",
                        "",
                        1.02,
                        Date(),
                        true
                    ),
                    ExchangeRate(
                        "USD",
                        "",
                        "EUR",
                        "",
                        1.02,
                        Date(),
                        true
                    )
                )
            ),
            navController = navController,
            modifier = Modifier

        ) {

        }
    }

}