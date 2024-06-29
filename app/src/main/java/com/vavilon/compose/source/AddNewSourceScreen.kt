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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun AddNewSourceScreen(
    state: SourceState,
    onEvent: (SourceEvent) -> Unit,
) {
    var selectedCategory: SourceCategories = state.sourceCategory
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onEvent(SourceEvent.HideDialog)
        },
        title = {
            Text(text = "Add New Source")
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SourceCategories.entries.forEach { category ->
                        Box(modifier = Modifier
                            .background(VavilonTheme.colors.backgroundIcon)
                            .height(30.dp)
                            .wrapContentSize()
                            .clickable {
                                selectedCategory = category
                                onEvent(SourceEvent.SetType(selectedCategory))
                                onEvent(SourceEvent.FilterSource(selectedCategory))
                            }) {
                            Text(
                                text = category.getSrcCategory(),
                                modifier = if (category != selectedCategory) {
                                    Modifier.alpha(0.5f)
                                } else {
                                    Modifier.background(VavilonTheme.colors.primaryText)
                                }
                            )
                        }
                    }
                }
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(SourceEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = "Source Title")
                    })
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(SourceEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Source Description")
                    })
                TextField(
                    value = state.balance.toString(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {
                        if (it.toDoubleOrNull() != null) {
                            onEvent(SourceEvent.SetBalance(it.toDouble()))
                        }
                    },
                    placeholder = {
                        Text(text = "Source Balance")
                    })
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(SourceEvent.SaveSource)
                }) {
                    Text(text = "Save")
                }
            }
        })
}

@Preview(showBackground = true, widthDp = 300, heightDp = 400)
@Composable
private fun PreviewAddWindow() {
    AddNewSourceScreen(state = SourceState(), onEvent = {})
}