package com.vavilon.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source

@Composable
fun SourceListView(state: SourceState,
                   onEvent: (SourceEvent) -> Unit,
                   ) {
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 15.dp, end = 10.dp)) {
        for(source:Source in state.sourceList) {
            SourceListItemView(source = source, onEvent = onEvent)
        }
    }
}