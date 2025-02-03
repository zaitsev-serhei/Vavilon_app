package com.vavilon.compose.plan

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
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
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.PlanState
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import kotlinx.coroutines.launch

@Composable
fun PlanDetailsEditScreen(
    planState: PlanState,
    sourceState: SourceState,
    onAddTransactionClick:()->Unit,
    onEvent: (UserEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf<SourceCategories?>(null) }

    var currentPlan = planState.currentPlan
    var planItems = planState.planedItemsMap

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SourceSelectionSheet(
                category = selectedCategory,
                sourceState = sourceState,
                onSourceSelected = { source ->
                    onEvent(UserEvent.PlanEventWrapper(PlanEvent.AddSourceToPlan(source.sourceId)))
                    Log.d("PlanDetails", "Source selected {$source}")
                    coroutineScope.launch { sheetState.hide() }
                }
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
            if (currentPlan != null) {
                Text(text = currentPlan.description)
            } else {
                Text(text = "Something went wrong!!!")
            }
            PlanSourceCategoryRowView(
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
fun PlanSourceCategoryRowView(
    planedItemsMap: Map<Source, List<Transaction>>,
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
                .height(50.dp)
                .background(backgroundColor), verticalAlignment = Alignment.CenterVertically
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
                            source.sourceType.equals(category.getSrcCategory())
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
                            SourceCategories.INCOME, SourceCategories.SAVING -> onAddSourceClick(category)
                            SourceCategories.EXPENSE -> onAddTransactionClick()
                        }
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

@Composable
fun SourceSelectionSheet(
    category: SourceCategories?,
    sourceState: SourceState,
    onSourceSelected: (Source) -> Unit
) {
    val availableSources = sourceState.sourceList.filter { source ->
        source.sourceType == category?.getSrcCategory()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.helpElement)
            .padding(16.dp)
    ) {
        Text(text = "Select the Source", style = Typography.body1)

        if (availableSources.isEmpty()) {
            Button(
                onClick = { /* Тут можно добавить логику для создания нового источника */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add new Source")
            }
        } else {
            availableSources.forEach { source ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSourceSelected(source) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = source.sourceDescription, style = Typography.body1)
                }
            }
        }
    }
}