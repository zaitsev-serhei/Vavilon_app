package com.vavilon.compose.source

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.TransactionCategories
import com.vavilon.model.dataHandlers.EntityItem
import com.vavilon.model.dataHandlers.SourceItemWrapper
import com.vavilon.model.dataHandlers.TransactionItemWrapper
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import java.util.Date

@Composable
fun <T : EntityItem> EntityItemRowView(
    item: T,
    modifier: Modifier,
    onEvent: (UserEvent) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        when (item) {
            is SourceItemWrapper -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user_default),
                    contentDescription = null,
                    tint = VavilonTheme.colors.secondaryElement,
                    modifier = Modifier.size(50.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1F)
                ) {
                    Text(text = item.title)
                    Text(text = item.description)
                }
                Text(
                    text = item.balance.toString(),
                    Modifier.padding(start = 5.dp, end = 10.dp)
                )
                Row(
                    Modifier
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onEvent(UserEvent.SourceEventWrapper(SourceEvent.EditSource(item.source)))
                            })
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(painter = painterResource(id = R.drawable.ic_trash_can),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onEvent(UserEvent.SourceEventWrapper(SourceEvent.DeleteSource(item.source)))
                            })
                }
            }

            is TransactionItemWrapper -> {
                val trIcon =
                    if (item.type == TransactionCategories.INCOME.getTransactionCategory()) {
                        R.drawable.ic_arrow_down
                    } else {
                        R.drawable.ic_arrow_top
                    }
                val trColor =
                    if (item.type == TransactionCategories.INCOME.getTransactionCategory()) {
                        VavilonTheme.colors.confirmButton // Green color for income
                    } else {
                        VavilonTheme.colors.canselButton // Red color for expense
                    }
                Icon(
                    painter = painterResource(id = trIcon),
                    contentDescription = null,
                    tint = VavilonTheme.colors.secondaryElement,
                    modifier = Modifier.size(50.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1F),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.title)
                    Text(text = item.description, maxLines = 1)
                }
                Text(text = item.transaction.transactionDate)
                Text(
                    style = Typography.h1,
                    color = trColor,
                    text = item.balance.toString(),
                    modifier = Modifier.padding(start = 20.dp, end = 10.dp)
                )

            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem() {
    VavilonTheme {
        EntityItemRowView(
            item = SourceItemWrapper(
                source = Source(
                    sourceType = "Income",
                    sourceTitle = "Job",
                    sourceDescription = "Job I love very much",
                    balance = 1250.0
                )
            ),
            onEvent = {}, modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem1() {
    VavilonTheme {
        EntityItemRowView(
            item = SourceItemWrapper(
                Source(
                    sourceType = "Income",
                    sourceTitle = "Job",
                    sourceDescription = "test",
                    balance = 1250.0
                )
            ),
            onEvent = {}, modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem2() {
    VavilonTheme {
        EntityItemRowView(
            item = TransactionItemWrapper(
                Transaction(
                    amount = 1250.0,
                    category = "Income",
                    description = "Salary",
                    date = Date().toString(),
                )
            ),
            onEvent = {}, modifier = Modifier
        )
    }
}