package com.vavilon.compose.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vavilon.compose.source.EntityItemRowView
import com.vavilon.model.dataHandlers.TransactionItemWrapper
import com.vavilon.model.events.UserEvent
import com.vavilon.storage.local.entities.TransactionEntity
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun VerticalTransactionListView(
    transactionList: List<TransactionEntity>?,
    userEvent: (UserEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(VavilonTheme.colors.backgroundUI)
            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
    ) {
        items(transactionList!!) { transaction ->
            EntityItemRowView(
                item = TransactionItemWrapper(transaction),
                modifier = Modifier.background(VavilonTheme.colors.primaryElement),
                onEvent = userEvent
            )
        }
    }
}