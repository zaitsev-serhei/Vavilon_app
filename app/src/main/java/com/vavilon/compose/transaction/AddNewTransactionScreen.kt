package com.vavilon.compose.transaction

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vavilon.model.TransactionCategories
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.states.TransactionState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddNewTransactionScreen(
    transactionState: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {
    var balanceText by remember { mutableStateOf(transactionState.amount.toString()) }
    var expanded by remember { mutableStateOf(false) }
    var currentCategory by remember { mutableStateOf(transactionState.transactionCategory) }
    LaunchedEffect(transactionState.transactionCategory) {
        currentCategory = transactionState.transactionCategory
    }
    AlertDialog(
        onDismissRequest = {
            onEvent(TransactionEvent.HideDialog)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add New Transaction",
                    style = Typography.h1,
                )
                TextField(
                    value = transactionState.description,
                    onValueChange = {
                        onEvent(TransactionEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(
                            text = "Transaction Title",
                            color = VavilonTheme.colors.darkText.copy(alpha = 0.5f)
                        )
                    })
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = currentCategory.getTransactionCategory(),
                        onValueChange = { onEvent(TransactionEvent.SetCategory(currentCategory)) },
                        placeholder = {
                            Text(
                                text = "Transaction Category",
                                color = VavilonTheme.colors.darkText.copy(alpha = 0.5f)
                            )
                        },
                        readOnly = true,
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        transactionState.categoriesList.forEach { categoryItem ->
                            DropdownMenuItem(
                                onClick = {
                                    Log.d("DropdownMenu", "Category selected: ${categoryItem}")
                                    currentCategory = TransactionCategories.entries.firstOrNull {
                                        it.getTransactionCategory() == categoryItem
                                    } ?: TransactionCategories.CUSTOM
                                    onEvent(TransactionEvent.SetCategory(currentCategory))
                                    expanded = false
                                    Log.d(
                                        "Add transaction",
                                        "Current state: ${transactionState.transactionCategory.getTransactionCategory()}"
                                    )
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
                            onEvent(TransactionEvent.SetAmount(newValue))
                        }
                    }
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    onEvent(TransactionEvent.SaveTransaction)
                }) {
                    Text(text = "Save")
                }
            }
        })
}