package com.vavilon.compose.plan

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.events.PlanEvent
import com.vavilon.storage.local.entities.Plan
import com.vavilon.ui.theme.Typography
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
            items(planList) {plan ->
                PlanCardItemView(plan = plan)
            }
        }
    }

}

@Composable
fun EmptyPlanListView(onEvent: (PlanEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "No Current Plan. Add one !", style = Typography.h1)
        Box(modifier = Modifier
            .padding(5.dp)
            .clickable {
                onEvent(PlanEvent.AddPlan)
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_button),
                contentDescription = null,
                Modifier.size(80.dp),
                tint = VavilonTheme.colors.backgroundIcon
            )
        }

    }
}
