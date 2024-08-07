package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vavilon.R
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.ui.theme.VavilonTheme
import com.vavilon.utils.Screen


@Composable
fun SourceRowScreen(
    sourceState: SourceState,
    navController: NavController,
    onEvent: (SourceEvent) -> Unit
) {
    if (sourceState.sourceList.isEmpty()) {
        EmptySourceListView(onEvent = onEvent)
    } else {
        LazyRow(
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(VavilonTheme.colors.backgroundUI)
                .clickable {
                    navController.navigate(Screen.SourceScreen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
        ) {
            items(sourceState.sourceList) { item ->
                SourceRowItemView(item)
            }
            item {
                AddButton(onEvent)
            }
        }
    }
}

@Composable
fun EmptySourceListView(onEvent: (SourceEvent) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.backgroundUI)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_source_list_us),
            Modifier.padding(5.dp),
            color = VavilonTheme.colors.primaryText
        )
        AddButton(onEvent)
    }
}

@Composable
fun AddButton(onEvent: (SourceEvent) -> Unit) {
    Box(modifier = Modifier.clickable {
        onEvent(SourceEvent.AddSource)
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_button),
            contentDescription = null,
            Modifier.size(80.dp),
            tint = VavilonTheme.colors.backgroundIcon
        )
    }
}
