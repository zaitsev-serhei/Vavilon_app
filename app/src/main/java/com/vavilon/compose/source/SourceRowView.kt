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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.vavilon.R
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Bronze
import com.vavilon.ui.theme.Gold
import com.vavilon.ui.theme.Midnight
import com.vavilon.utils.Screen


@Composable
fun SourceRowScreen(sourceState: SourceState,
                    navController: NavController,
                    onEvent: (SourceEvent) -> Unit) {
    if (sourceState.sourceList.isEmpty()) {
        EmptySourceListView(onEvent = onEvent)
    } else {
        LazyRow(
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Midnight)
                .clickable { navController.navigate(Screen.SourceScreen.route) }
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
            .background(Midnight)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_source_list_us),
            Modifier.padding(5.dp),
            color = Gold
        )
        AddButton(onEvent)
    }
}

@Composable
fun AddButton(onEvent: (SourceEvent) -> Unit) {
    Box(modifier = Modifier.clickable {
        onEvent(SourceEvent.ShowDialog)
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_button),
            contentDescription = null,
            Modifier.size(80.dp),
            tint = Bronze
        )
    }
}

@Preview(showBackground = true, heightDp = 200)
@Composable
private fun SourceRowPreview() {
    val source1: Source = Source("work", "work", "", 1000.0)
    val source2: Source = Source("work", "work", "", 1000.0)
    val source3: Source = Source("work", "work", "", 1000.0)
    val source4: Source = Source("work", "work", "", 1000.0)
    val source5: Source = Source("work", "work", "", 1000.0)
    val tempList = listOf(source1, source2)
    val liveDataList = MutableLiveData<List<Source>>()
    // liveDataList.value = tempList
    val state = SourceState(tempList)
   // SourceRowScreen(state, navController = NavController(),{})
}
