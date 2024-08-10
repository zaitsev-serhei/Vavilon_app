package com.vavilon.compose.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vavilon.compose.source.SourceCategoryRowView
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.states.TransactionState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun AddNewTransactionScreen(
    transactionState: TransactionState,
    onEvent: (TransactionEvent)-> Unit) {
    var balanceText by remember { mutableStateOf(transactionState.amount.toString()) }
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
                TextField(
                    value = transactionState.transactionCategory.getTransactionCategory(),
                    onValueChange = {
                        onEvent(TransactionEvent.SetCategory(transactionState.transactionCategory))
                    },
                    placeholder = {
                        Text(
                            text = "Transaction Category",
                            color = VavilonTheme.colors.darkText.copy(alpha = 0.5f)
                        )
                    })
                TextField(
                    value = balanceText,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange =   {
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
                    .fillMaxWidth()
                ,
                contentAlignment = Alignment.Center
            ) {
                Button( onClick = {
                    onEvent(TransactionEvent.SaveTransaction)
                }) {
                    Text(text = "Save")
                }
            }
        })
}