package com.vavilon.compose.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.SourceCategories
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun HomeStatisticMenu(
    sourceState: SourceState
) {
    val menuHeight = 150.dp
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(menuHeight)
            .background(VavilonTheme.colors.backgroundUI)
    ) {
        val cardWidth = maxWidth / 2
        val cardHeight = maxHeight / 2

        Column(
            modifier = Modifier
                .fillMaxSize(),
            //verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                SourceAdaptiveCard(
                    sourceState = sourceState,
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    modifier = Modifier.weight(1f)
                )
                SourceAdaptiveCard(
                    sourceState = sourceState,
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                SourceAdaptiveCard(
                    sourceState = sourceState,
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    modifier = Modifier.weight(1f)
                )
                SourceAdaptiveCard(
                    sourceState = sourceState,
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
    val incomeSourceCounter = sourceState.sourceList.count {
        it.sourceType == SourceCategories.INCOME.getSrcCategory()
    }
    val expenseSourceCounter = sourceState.sourceList.count {
        it.sourceType == SourceCategories.EXPENSE.getSrcCategory()
    }
    val savingSourceCounter = sourceState.sourceList.count {
        it.sourceType == SourceCategories.SAVING.getSrcCategory()
    }

    Card(
        modifier = modifier
            .padding(5.dp)
            .width(cardWidth)
            .height(cardHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                ,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Source Limits",
                style = Typography.body1)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.3f)
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
                    modifier = Modifier.size(cardHeight * 0.3f)
                )
                Text(
                    text = "$expenseSourceCounter\\${sourceState.limitCount}",
                    //modifier = Modifier.size(cardHeight * 0.3f),
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
                    modifier = Modifier.size(cardHeight * 0.3f)
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
    val incomeTransactionCounter = transactionState.transactionList.count()
    val expenseTransactionCounter = transactionState.transactionList.count ()
    val totalTransactionCounter = transactionState.transactionList.count()

    Card(
        modifier = modifier
            .padding(5.dp)
            .width(cardWidth)
            .height(cardHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Transactions This Month",
                style = Typography.body1)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(cardHeight * 0.3f)
                )
                Text(
                    text = "Tr",
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
                    modifier = Modifier.size(cardHeight * 0.3f)
                )
                Text(
                    text = "Tr",
                    //modifier = Modifier.size(cardHeight * 0.3f),
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
                    modifier = Modifier.size(cardHeight * 0.3f)
                )
                Text(
                    text = "Tr",
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
        HomeStatisticMenu(sourceState = SourceState())//, transactionState = TransactionState() )
    }

}