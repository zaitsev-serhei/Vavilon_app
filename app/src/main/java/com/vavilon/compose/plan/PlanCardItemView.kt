package com.vavilon.compose.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.ItemStatus
import com.vavilon.storage.local.entities.PlanEntity
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun PlanCardItemView(
    plan: PlanEntity,
    onPlanItemClick: () -> Unit
) {
    val itemBackGroundColor = when (ItemStatus.entries.firstOrNull { status ->
        status == plan.status
    } ?: ItemStatus.PLANNED) {
        ItemStatus.INPROCESS -> VavilonTheme.colors.income5
        ItemStatus.PLANNED -> VavilonTheme.colors.lightText
        ItemStatus.COMPLETE -> VavilonTheme.colors.savings3
        else -> {
            VavilonTheme.colors.savings3
        }
    }
    val textColor = when (ItemStatus.entries.firstOrNull { status ->
        status == plan.status
    } ?: ItemStatus.PLANNED) {
        ItemStatus.INPROCESS -> VavilonTheme.colors.helpText
        ItemStatus.PLANNED -> VavilonTheme.colors.darkText
        ItemStatus.COMPLETE -> VavilonTheme.colors.primaryText
        else -> {
            VavilonTheme.colors.primaryText
        }
    }
    Card(
        Modifier
            .height(110.dp)
            .width(250.dp)
            .shadow(
                4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onPlanItemClick() },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = itemBackGroundColor
    ) {
        Column(
            Modifier
                .padding(start = 5.dp, end = 5.dp, top = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = plan.status.getItemStatus(),
                    style = Typography.body2,
                    color = textColor,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(VavilonTheme.colors.primaryText)
                )

            }
            Text(
                text = plan.description + " (${plan.completedTransactionCount}/${plan.transactionCount})",//source.sourceTitle
                style = Typography.h2,
                color = textColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = plan.startDate,
                    style = Typography.body2,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = plan.endDate,//source.sourceTitle
                    style = Typography.body2,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlanCardPreview() {
    VavilonTheme {
        val calendar = Calendar.getInstance()
        val startDate =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        val plan =
            PlanEntity(
                "Plan January 2024",
                ItemStatus.INPROCESS,
                startDate,
                startDate,
                startDate
            )
        plan.transactionCount = 15
        plan.completedTransactionCount = 8
        PlanCardItemView(plan = plan, {})
    }
}