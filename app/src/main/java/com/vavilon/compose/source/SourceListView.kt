package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import androidx.compose.ui.graphics.Color
import com.vavilon.ui.theme.BlueGreen
import com.vavilon.ui.theme.Crimson
import com.vavilon.ui.theme.DarkBlue
import com.vavilon.ui.theme.LightGreen
import com.vavilon.ui.theme.RoyalBlue

@Composable
fun SourceListView(state: SourceState,
                   onEvent: (SourceEvent) -> Unit,
                   ) {
    val backgroundColor: Color = when (state.sourceCategories) {
        SourceCategories.INCOME -> LightGreen
        SourceCategories.EXPENSE -> Crimson
        SourceCategories.SAVING -> RoyalBlue
        else -> BlueGreen
    }
    Column(modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(top = 10.dp, start = 15.dp, end = 15.dp)
        ) {
        for(source:Source in state.sourceList) {
            SourceListItemView(source = source, modifier = Modifier.background(backgroundColor), onEvent = onEvent)
        }
    }
}