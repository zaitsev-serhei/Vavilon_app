package com.vavilon.compose.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vavilon.model.events.PlanEvent
import com.vavilon.storage.local.entities.Plan
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun PlanHorizontalCardView(
    modifier: Modifier,
    planList: List<Plan>,
    onEvent: (PlanEvent) -> Unit
) {
    if (planList.isEmpty()) {
        EmptyPlanListView(onEvent)
    } else {
        LazyRow(
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(VavilonTheme.colors.backgroundUI)
        ) {

        }
    }

}

@Composable
fun EmptyPlanListView(onEvent: (PlanEvent) -> Unit) {
    TODO("Not yet implemented")
}
