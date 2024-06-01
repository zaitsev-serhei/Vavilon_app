package com.vavilon.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
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
import com.vavilon.ui.theme.Bronze
import com.vavilon.ui.theme.DarkBlue
import com.vavilon.ui.theme.Gold
import com.vavilon.ui.theme.Midnight
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
            .background(DarkBlue),
        topBar = {
            TopBar(
                userIcon = R.drawable.ic_user_default,
                welcomeText = R.string.welcomeText_us,
                modifier = Modifier,
                userName = "Serhii"
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
                .background(DarkBlue)
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
    @DrawableRes userIcon: Int,
    @StringRes welcomeText: Int,
    modifier: Modifier,
    userName: String
) {
    Surface {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Midnight),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(userIcon),
                    contentDescription = null,
                    tint = Bronze,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(start = 10.dp, top = 5.dp),
                    )
                Text(
                    text = stringResource(id = welcomeText) + userName,
                    color = Gold
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = Bronze,
                    modifier = Modifier.padding(end = 15.dp, top = 5.dp)
                )
            }
            CurrentBalanceView()
            CurrentStatisticView()
        }
    }
}

@Composable
fun CurrentBalanceView() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Midnight)
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.current_balance_us),
                style = Typography.h1.copy(color = Gold))
            Text(text = "$ 10000", color = Gold)
        }
    }

}

@Composable
fun CurrentStatisticView() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Midnight)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp), Arrangement.SpaceBetween
        ) {
            Text(text = "test1", color = Gold)
            Text(text = "test2", color = Gold)
        }
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(text = "test3", color = Gold)
        }

    }
}

@Composable
@Preview(showBackground = true, heightDp = 550)
fun HomeScreenPreview() {
    VavilonTheme {
        val source1 = Source("work", "work", "", 1000.0)
        val source2 = Source("work", "work", "", 1000.0)
        val source3 = Source("work", "work", "", 1000.0)
        val source4 = Source("work", "work", "", 1000.0)
        val source5 = Source("work", "work", "", 1000.0)
        val tempList = listOf(source1, source2, source3, source4, source5)
        val liveDataList = MutableLiveData<List<Source>>()
        val state = SourceState(tempList)
        //liveDataList.value = tempList
        val navController = rememberNavController()

        HomeScreenView(modifier = Modifier, state, navController = navController, onEvent = {})
    }

}