package com.vavilon.compose.source

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.SourceState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun AddNewSourceScreen(
    state: SourceState,
    onEvent: (UserEvent) -> Unit,
    onSaved: () -> Unit
) {
//    var selectedCategory: SourceCategories = state.sourceCategory
    var balanceText by remember { mutableStateOf(state.balance.toString()) }
    val sourceEventHandler: (SourceEvent) -> Unit = { event ->
        onEvent(UserEvent.SourceEventWrapper(event))
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Source",
            style = Typography.h1,
        )
        SourceCategoryRowView(onEvent = sourceEventHandler)
        TextField(
            value = state.name,
            onValueChange = {
                onEvent(UserEvent.SourceEventWrapper(SourceEvent.SetName(it)))
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
                onEvent(UserEvent.SourceEventWrapper(SourceEvent.SetDescription(it)))
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
                    onEvent(UserEvent.SourceEventWrapper(SourceEvent.SetBalance(newValue)))
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                if (state.name.isNotBlank() || state.description.isNotBlank()){
                    onEvent(UserEvent.SourceEventWrapper(SourceEvent.SaveSource))
                    Log.d("Source to save", "New Source: ${state.isAddingNewSource}")
                    onSaved()
                }
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
private fun PreviewAddWindow() {
    VavilonTheme {
        AddNewSourceScreen(state = SourceState(), onEvent = {}, onSaved = {})
    }
}