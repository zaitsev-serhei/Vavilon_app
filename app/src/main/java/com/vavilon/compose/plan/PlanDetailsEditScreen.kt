package com.vavilon.compose.plan

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.compose.transaction.VerticalTransactionListView
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.PlanEvent
import com.vavilon.model.states.PlanState
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun PlanDetailsEditScreen(
    planState: PlanState,
    sourceState: SourceState,
    transactionState: TransactionState,
    onEvent: (PlanEvent) -> Unit
) {
    var currentPlan = planState.currentPlan
    var planItems = planState.planedItemsMap
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        if (currentPlan != null) {
            Text(text = currentPlan.description)
        } else {
            Text(text = "Something went wrong!!!")
        }
        PlanSourceCategoryRowView(planState.planedItemsMap)
    }
}

@Composable
fun PlanSourceCategoryRowView(planedItemsMap: Map<Source, List<Transaction>>) {
    val context = LocalContext.current

    SourceCategories.entries.forEach { category ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VavilonTheme.colors.backgroundUI)
        ) {
            Text(text = category.sourceCategory)
            Text(
                text = "${
                    planedItemsMap.keys.count { source ->
                        source.sourceType.equals(category.getSrcCategory())
                    }
                }"
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add_button),
                contentDescription = null,
                modifier = Modifier.size(20.dp).clickable {
                    Toast
                        .makeText(
                            context,
                            "To be implemented: adding new items",
                            Toast.LENGTH_LONG
                        )
                        .show()
                },
                tint = VavilonTheme.colors.backgroundIcon
            )
        }
        val currentSource =
            planedItemsMap.keys.find { source -> source.sourceType.equals(category.getSrcCategory()) }
        if (currentSource != null) {
            VerticalTransactionListView(transactionList = planedItemsMap[currentSource]) {
            }
        } else {
            Text(text = "No Transaction for the Source!")
        }
    }
}