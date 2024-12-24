package com.vavilon.compose.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.SourceCategories
import com.vavilon.model.TransactionCategories
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun HomeCurrencyRates(

) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(VavilonTheme.colors.backgroundUI)
    ) {
        val cardWidth = maxWidth
        val cardHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CurrencyExchangeAdaptiveCard(
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    modifier = Modifier.weight(1f)
                )
                CryptoAdaptiveCard(
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun SourceAdaptiveCard(
    sourceState: SourceState,
    cardWidth: Dp,
    cardHeight: Dp,
    modifier: Modifier = Modifier
) {
    val incomeSourceCounter =
        sourceState.sourceCounterMap[SourceCategories.INCOME.getSrcCategory()] ?: 0
    val expenseSourceCounter =
        sourceState.sourceCounterMap[SourceCategories.EXPENSE.getSrcCategory()] ?: 0
    val savingSourceCounter =
        sourceState.sourceCounterMap[SourceCategories.SAVING.getSrcCategory()] ?: 0

    Card(
        modifier = modifier
            .padding(5.dp)
            .width(cardWidth)
            .height(cardHeight)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Source Limits",
                style = Typography.body1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$incomeSourceCounter\\${sourceState.limitCount}",
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_top),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$expenseSourceCounter\\${sourceState.limitCount}",
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bank_piggy),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$savingSourceCounter\\${sourceState.limitCount}",
                    style = Typography.body1
                )
            }
        }
    }
}

@Composable
fun TransactionAdaptiveCard(
    transactionState: TransactionState,
    cardWidth: Dp,
    cardHeight: Dp,
    modifier: Modifier = Modifier
) {
    val incomeTransactionCounter = transactionState.transactionList.count {
        it.category == (TransactionCategories.INCOME.getTransactionCategory())
    }
    val expenseTransactionCounter = transactionState.transactionList.count {
        (it.category != (TransactionCategories.INCOME.getTransactionCategory()))
    }
    val totalTransactionCounter = transactionState.transactionList.size
    val textSize = with(LocalDensity.current) { (cardHeight * 0.12f).toSp() }
    Card(
        modifier = modifier
            .padding(5.dp)
            .width(cardWidth)
            .height(cardHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Transactions This Month",
                maxLines = 1,
                style = Typography.body1.copy(fontSize = textSize)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$incomeTransactionCounter",
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_top),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$expenseTransactionCounter",
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_transaction_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$totalTransactionCounter",
                    style = Typography.body1
                )
            }
        }
    }
}

@Composable
fun CurrencyExchangeAdaptiveCard(
    cardWidth: Dp,
    cardHeight: Dp,
    modifier: Modifier = Modifier
) {
    val exchange1 = "41,2 / 41,8"
    val exchange2 = "42,2 / 42,8"
    val exchange3 = "7.2 / 8.1"
    Card(
        modifier = modifier
            .padding(5.dp)
            .width(cardWidth)
            .height(cardHeight)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Currency Rates",
                maxLines = 1,
                style = Typography.body1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dollar_sign),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = exchange1,
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_euro_sign),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = exchange2,
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pound_sign),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = exchange3,
                    style = Typography.body1
                )
            }
        }
    }
}

@Composable
fun CryptoAdaptiveCard(
    cardWidth: Dp,
    cardHeight: Dp,
    modifier: Modifier = Modifier
) {
    val exchange1 = "70120"
    val exchange2 = "3800"
    val exchange3 = "85"
    val currency = "USDT"
    Card(
        modifier = modifier
            .padding(5.dp)
            .width(cardWidth)
            .height(cardHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crypto Rates",
                maxLines = 1,
                style = Typography.body1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bitcoin_sign),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$exchange1 $currency",
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ethereum_sign),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.25f)
                )
                Text(
                    text = "$exchange2 $currency",
                    style = Typography.body1
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_litecoin_sign),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.2f)
                )
                Text(
                    text = "$exchange3 $currency",
                    style = Typography.body1
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 250)
@Composable
private fun PreviewManu() {
    VavilonTheme {
        HomeCurrencyRates(
        )//, transactionState = TransactionState() )
    }

}