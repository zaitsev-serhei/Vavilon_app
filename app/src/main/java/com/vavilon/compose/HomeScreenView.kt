package com.vavilon.compose

import android.content.res.Configuration
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.vavilon.compose.menu.BottomNavigation
import com.vavilon.compose.source.AddNewSourceScreen
import com.vavilon.compose.source.SourceCategoryRowView
import com.vavilon.compose.source.SourceRowScreen
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun HomeScreenView(
    modifier: Modifier,
    state: SourceState,
    navController: NavController,
    onEvent: (SourceEvent) -> Unit
) {
    if (state.isAddingNewSource) {
        AddNewSourceScreen(state = state, onEvent = onEvent)
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
                state = state
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
            SourceRowScreen(state, navController, onEvent)
            Spacer(modifier = Modifier.height(10.dp))
            SourceCategoryRowView(onEvent = onEvent)
            Spacer(modifier = Modifier.height(10.dp))
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
    Row(
        Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.backgroundUI)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 5.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.current_balance_us),
                style = Typography.overline,
                color = VavilonTheme.colors.primaryText.copy(alpha = 0.7F)
            )
            Text(
                text = "${state.currentBalance} + ${state.totalSavings}",
                style = Typography.h1
            )
        }
    }

}

@Composable
fun CurrentStatisticView(state: SourceState) {
    val labels = listOf(R.string.income_balance_us,
        R.string.expense_balance_us,
        R.string.saving_balance_us)
    val balances = listOf(state.totalIncome,
        state.totalExpense,
        state.totalSavings)
    val backgroundColors = listOf(VavilonTheme.colors.income2,
        VavilonTheme.colors.expense2,
        VavilonTheme.colors.savings2)
    Row(
        Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.backgroundUI),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        labels.forEach { item ->
            Card(modifier = Modifier
                .size(height = 40.dp, width = 100.dp)
                .padding(start = 10.dp, top = 5.dp)
                .background(backgroundColors[1])
            ) {
                Text(text = stringResource(id = item),
                    style = Typography.caption)
                Text(text = balances.get(1).toString() )
            }
        }

    }
}

@Composable
@Preview(
    showBackground = true, heightDp = 550, device = "id:Nexus S",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
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
        HomeScreenView(modifier = Modifier, state, navController = navController, onEvent = {})
    }

}