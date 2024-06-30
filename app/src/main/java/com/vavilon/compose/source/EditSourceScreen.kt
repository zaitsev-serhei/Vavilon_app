package com.vavilon.compose.source

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState

@Composable
fun EditSourceScreen(state: SourceState, onEvent: (SourceEvent) -> Unit) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onEvent(SourceEvent.HideDialog)
        },
        title = {
            Text(text = "Edit Source")
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Log.d("Edit screen", "Current state: ${state.toString()}")
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