package com.vavilon.compose.plan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.compose.transaction.VerticalTransactionListView
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.PlanEvent
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.PlanState
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.TransactionEntity
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import kotlinx.coroutines.launch

@Composable
fun PlanDetailsEditScreen(
    planState: PlanState,
    sourceState: SourceState,
    onAddTransactionClick: () -> Unit,
    onAddSourceButtonClick: () -> Unit,
    onEvent: (UserEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf<SourceCategories?>(null) }

    val planItems = planState.planedItemsMap

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SourceSelectionSheet(
                category = selectedCategory,
                selectedSourcesSet = planItems.keys,
                sourceState = sourceState,
                onSourceSelected = { source ->
                    onEvent(UserEvent.PlanEventWrapper(PlanEvent.AddSourceToPlan(source.sourceId)))
                    onEvent(
                        UserEvent.TransactionEventWrapper(
                            TransactionEvent.AddTransactionToPlan(
                                source,
                                planState.currentPlan.id
                            )
                        )
                    )
                    Log.d("PlanDetails", "Source selected {$source}")
                    coroutineScope.launch { sheetState.hide() }
                },
                onAddSourceButtonClick = onAddSourceButtonClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VavilonTheme.colors.backgroundUI),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            val currentPlan = planState.currentPlan
            if (currentPlan.description.isBlank()) {
                Text(text = "Something went wrong!!!")
            } else {
                Text(text = currentPlan.description)
            }
            PlanItemsRowByCategoryView(
                planedItemsMap = planState.planedItemsMap,
                onAddSourceClick = { category: SourceCategories ->
                    selectedCategory = category
                    coroutineScope.launch { sheetState.show() }
                },
                onAddTransactionClick = onAddTransactionClick
            )
        }
    }
}


@Composable
fun PlanItemsRowByCategoryView(
    planedItemsMap: Map<SourceEntity, List<TransactionEntity>>,
    onAddSourceClick: (SourceCategories) -> Unit,
    onAddTransactionClick: () -> Unit
) {
    val context = LocalContext.current
    SourceCategories.entries.forEach { category ->
        var backgroundColor = when (category) {
            SourceCategories.INCOME -> VavilonTheme.colors.income5
            SourceCategories.EXPENSE -> VavilonTheme.colors.lightText
            SourceCategories.SAVING -> VavilonTheme.colors.savings3
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(text = category.sourceCategory)
                Text(
                    text = " ${
                        planedItemsMap.keys.count { source ->
                            source.sourceType == category.getSrcCategory()
                        }
                    }"
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_add_button),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 5.dp)
                    .clickable {
                        when (category) {
                            SourceCategories.INCOME, SourceCategories.SAVING -> onAddSourceClick(
                                category
                            )

                            SourceCategories.EXPENSE -> onAddTransactionClick()
                        }
                    },
                tint = VavilonTheme.colors.backgroundIcon
            )
        }
        val currentSource =
            planedItemsMap.keys.filter { source -> source.sourceType == category.getSrcCategory() }
        if (currentSource != null) {
            currentSource.forEach { source ->
                VerticalTransactionListView(transactionList = planedItemsMap[source]) {
                }
            }

        } else {
            Text(text = "No Transaction for the Source!")
        }
    }
}

@Composable
fun SourceSelectionSheet(
    category: SourceCategories?,
    selectedSourcesSet: Set<SourceEntity>,
    sourceState: SourceState,
    onSourceSelected: (SourceEntity) -> Unit,
    onAddSourceButtonClick: () -> Unit
) {
    Log.d("PlanDetails", "Selected Source selected {$selectedSourcesSet}")
    val availableSources = sourceState.sourceList.filter { source ->
        source.sourceType == category?.getSrcCategory() && !selectedSourcesSet.contains(source)
    }
    Log.d("PlanDetails", "Available Source selected {$availableSources}")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.helpElement)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Select the Source", style = Typography.body1)

        if (availableSources.isEmpty()) {
            Button(
                onClick = { onAddSourceButtonClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add new Source")
            }
        } else {
            availableSources.forEach { source ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable { onSourceSelected(source) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = source.sourceDescription, style = Typography.body1)
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}