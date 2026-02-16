package com.vavilon.compose.transaction

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.vavilon.model.SourceCategories
import com.vavilon.model.TransactionCategories
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddNewTransactionScreen(
    transactionState: TransactionState,
    sourceState: SourceState,
    onEvent: (UserEvent) -> Unit,
    onSaved: () -> Unit
) {
    var balanceText by remember { mutableStateOf(transactionState.amount.toString()) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var sourceExpanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var currentCategory by remember { mutableStateOf(transactionState.transactionCategory) }
    var sourceId by remember {
        mutableStateOf(transactionState.currentSourceId)
    }
    val icon = if (categoryExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    LaunchedEffect(transactionState.transactionCategory) {
        currentCategory = transactionState.transactionCategory
    }
    LaunchedEffect(Unit) {
        categoryExpanded = false
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Transaction",
            style = Typography.h1,
        )
        //update for income source
        ExposedDropdownMenuBox(
            expanded = sourceExpanded,
            onExpandedChange = { sourceExpanded = !sourceExpanded }
        ) {
            OutlinedTextField(
                value = sourceState.sourceList.find { source -> source.sourceId == sourceId }?.sourceTitle
                    ?: "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 10.dp)
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize() // Запоминаем размер TextField
                    },
                label = { Text("Select Source") },
                trailingIcon = {
                    Icon(icon, "contentDescription", Modifier.clickable { sourceExpanded = !sourceExpanded })
                },
                readOnly = true // Поле только для чтения
            )
            DropdownMenu(
                expanded = sourceExpanded,
                onDismissRequest = { sourceExpanded = false },
                modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                sourceState.sourceList.filter { source -> source.sourceType.equals(SourceCategories.INCOME.getSrcCategory())}.forEach { sourceItem ->
                    DropdownMenuItem(
                        onClick = {
                            Log.d("DropdownMenu", "Source selected: ${sourceItem}")
                            sourceId = sourceItem.sourceId
                            onEvent(
                                UserEvent.TransactionEventWrapper(
                                    TransactionEvent.SetSourceId(
                                        sourceId
                                    )
                                )
                            )
                            sourceExpanded = false
                        },
                        content = { Text(sourceItem.sourceTitle) }
                    )
                }
            }
        }
        TextField(
            value = transactionState.description,
            onValueChange = {
                onEvent(UserEvent.TransactionEventWrapper(TransactionEvent.SetDescription(it)))
            },
            placeholder = {
                Text(
                    text = "Transaction Title",
                    color = VavilonTheme.colors.darkText.copy(alpha = 0.5f)
                )
            })

        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded }
        ) {
            OutlinedTextField(
                value = currentCategory.getTransactionCategory(),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 10.dp)
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize() // Запоминаем размер TextField
                    },
                label = { Text("Select Category") },
                trailingIcon = {
                    Icon(icon, "contentDescription", Modifier.clickable { categoryExpanded = !categoryExpanded })
                },
                readOnly = true // Поле только для чтения
            )
            DropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false },
                modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                transactionState.categoriesList.forEach { categoryItem ->
                    DropdownMenuItem(
                        onClick = {
                            Log.d("DropdownMenu", "Category selected: ${categoryItem}")
                            currentCategory = TransactionCategories.entries.firstOrNull {
                                it.getTransactionCategory() == categoryItem
                            } ?: TransactionCategories.CUSTOM
                            onEvent(
                                UserEvent.TransactionEventWrapper(
                                    TransactionEvent.SetCategory(
                                        currentCategory
                                    )
                                )
                            )
                            categoryExpanded = false
                        },
                        content = { Text(categoryItem) }
                    )
                }
            }
        }
        TextField(
            value = balanceText,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                balanceText = it
                it.toDoubleOrNull()?.let { newValue ->
                    onEvent(
                        UserEvent.TransactionEventWrapper(
                            TransactionEvent.SetAmount(
                                newValue
                            )
                        )
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    Log.d(
                        "Add category screen",
                        "Category before save ${currentCategory.getTransactionCategory()}"
                    )
                    if (transactionState.amount > 0 && currentCategory != TransactionCategories.ALL) {
                        onEvent(UserEvent.TransactionEventWrapper(TransactionEvent.SaveTransaction))
                        onSaved()
                    }
                }) {
                Text(text = "Save")
            }
        }
    }
}
