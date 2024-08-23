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
import com.vavilon.model.dataHandlers.SourceItemWrapper
import com.vavilon.model.events.UserEvent
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun SourceListView(
    state: SourceState,
    onEvent: (UserEvent) -> Unit,
) {
    val backgroundColor: Color = when (state.sourceCategory) {
        SourceCategories.INCOME -> VavilonTheme.colors.income2
        SourceCategories.EXPENSE -> VavilonTheme.colors.expense2
        SourceCategories.SAVING -> VavilonTheme.colors.savings2
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.backgroundUI)
            .padding(top = 10.dp, start = 15.dp, end = 15.dp)
    ) {
        for (source: Source in state.sourceList) {
            SourceListItemView(
                item = SourceItemWrapper(source),
                modifier = Modifier.background(backgroundColor),
                onEvent = onEvent
            )
        }
    }
}