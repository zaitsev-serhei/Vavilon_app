package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun AddNewSourceScreen(
    state: SourceState,
    onEvent: (SourceEvent) -> Unit,
) {
    var selectedCategory: SourceCategories = state.sourceCategory
    var balanceText by remember { mutableStateOf(state.balance.toString()) }
    AlertDialog(
        onDismissRequest = {
            onEvent(SourceEvent.HideDialog)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add New Source",
                    style = Typography.h1,
                )
                SourceCategoryRowView(onEvent = onEvent)
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(SourceEvent.SetName(it))
                    },
                    placeholder = {
                        Text(
                            text = "Source Title",
                            color = VavilonTheme.colors.darkText.copy(alpha = 0.5f)
                        )
                    })
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(SourceEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(
                            text = "Source Description",
                            color = VavilonTheme.colors.darkText.copy(alpha = 0.5f)
                        )
                    })
                TextField(
                    value = balanceText,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {
                        balanceText = it
                        it.toDoubleOrNull()?.let { newValue ->
                            onEvent(SourceEvent.SetBalance(newValue))
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
                    onEvent(SourceEvent.SaveSource)
                }) {
                    Text(text = "Save")
                }
            }
        })
}

@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
private fun PreviewAddWindow() {
    VavilonTheme {
        AddNewSourceScreen(state = SourceState(), onEvent = {})
    }
}