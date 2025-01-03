package com.vavilon.compose

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vavilon.R
import com.vavilon.compose.menu.ActionButtonsRow
import com.vavilon.compose.menu.BottomNavigation
import com.vavilon.compose.menu.HomeCurrencyRates
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun HomeScreenView(
    sourceState: SourceState,
    transactionState: TransactionState,
    navController: NavController,
    onEvent: (UserEvent) -> Unit,
    onAddSource: () -> Unit,
    onAddTransaction: () -> Unit,
    onSaved: () -> Unit
) {
    val sourceEventHandler: (SourceEvent) -> Unit = { event ->
        onEvent(UserEvent.SourceEventWrapper(event))
    }
    val transactionEventHandler: (TransactionEvent) -> Unit = { event ->
        onEvent(UserEvent.TransactionEventWrapper(event))
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(VavilonTheme.colors.backgroundUI),
        topBar = {
            TopBar(
                welcomeText = R.string.welcomeText_us,
                modifier = Modifier,
                userName = "Serhii",
                state = sourceState
            )
        },
        bottomBar = {
            BottomNavigation(
                navController = navController
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VavilonTheme.colors.backgroundUI)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        )
        {
            Spacer(modifier = Modifier.height(10.dp))
            HomeCurrencyRates()
            Spacer(modifier = Modifier.height(10.dp))
            ActionButtonsRow (onAddSource = onAddSource,
                onAddTransaction = onAddTransaction)
        }
    }
}

@Composable
fun TopBar(
    @StringRes welcomeText: Int,
    modifier: Modifier,
    state: SourceState,
    userName: String
) {
    val context = LocalContext.current
    Surface {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(VavilonTheme.colors.backgroundUI),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user_default),
                    contentDescription = null,
                    tint = VavilonTheme.colors.backgroundIcon,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(start = 10.dp, top = 5.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "To be implemented",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        },
                )
                Text(
                    text = stringResource(id = welcomeText) + userName,
                    color = VavilonTheme.colors.primaryText
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = VavilonTheme.colors.backgroundIcon,
                    modifier = Modifier
                        .padding(end = 15.dp, top = 5.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "To be implemented",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                )
            }
            CurrentBalanceView(state = state)
            CurrentStatisticView(state = state)
        }
    }
}

@Composable
fun CurrentBalanceView(state: SourceState) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(VavilonTheme.colors.backgroundUI),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.current_balance_us),
                style = Typography.h1,
                color = VavilonTheme.colors.primaryText.copy(alpha = 0.7F)
            )
            Text(
                text = "${state.currentBalance}",
                style = Typography.h1
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(25.dp)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "To be implemented",
                            Toast.LENGTH_LONG
                        )
                        .show()
                },
            tint = VavilonTheme.colors.backgroundIcon
        )
    }
}

@Composable
fun CurrentStatisticView(state: SourceState) {
    val balances = listOf(
        state.totalIncome,
        state.totalExpense,
        state.totalSavings
    )
    val labels = listOf(
        R.string.earn_month,
        R.string.spent_month,
        R.string.saved_month,
    )
    val backgroundColors = listOf(
        VavilonTheme.colors.income2,
        VavilonTheme.colors.expense2,
        VavilonTheme.colors.savings2
    )
    val icons = listOf(
        R.drawable.ic_arrow_down,
        R.drawable.ic_arrow_top,
        R.drawable.ic_bank_piggy
    )
    Row(
        Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.backgroundUI),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        balances.forEachIndexed { index, item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = labels[index]),
                    style = Typography.body2)
                Card(
                    modifier = Modifier
                        .size(height = 40.dp, width = 100.dp)
                        .padding(start = 10.dp, top = 5.dp, end = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(backgroundColors[index])
                            .padding(start = 3.dp, end = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = balances[index].toString(),
                            style = Typography.body1,
                            maxLines = 1
                        )
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    device = "id:Nexus S",
)
fun HomeScreenPreview() {
    VavilonTheme {
        val source1 = Source("work", "work", "", 1000.0)
        val source2 = Source("work", "work", "", 1000.0)
        val source3 = Source("work", "work", "", 1000.0)
        val source4 = Source("work", "work", "", 1000.0)
        val tempList = listOf(source1, source2, source3, source4)
        val state = SourceState(tempList)
        val navController = rememberNavController()
        HomeScreenView(
            state,
            transactionState = TransactionState(),
            navController = navController,
            onAddSource = {},
            onAddTransaction = {},
            onEvent = {},
            onSaved = {}
        )
    }
}