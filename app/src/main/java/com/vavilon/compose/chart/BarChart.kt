package com.vavilon.compose.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.vavilon.model.TransactionCategories
import com.vavilon.model.states.TransactionState
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme
import java.util.Date

@Composable
fun BarChartTransaction(transactionState: TransactionState) {
    val transactionColors = mapOf(
        TransactionCategories.RENT.getTransactionCategory() to VavilonTheme.colors.expense1,
        TransactionCategories.FOOD.getTransactionCategory() to VavilonTheme.colors.expense2,
        TransactionCategories.TRAVEL.getTransactionCategory() to VavilonTheme.colors.expense3,
        TransactionCategories.TAXES.getTransactionCategory() to VavilonTheme.colors.expense4,
        TransactionCategories.HOBBY.getTransactionCategory() to VavilonTheme.colors.helpElement,
        TransactionCategories.RESTAURANTS.getTransactionCategory() to VavilonTheme.colors.income1,
        TransactionCategories.ONETIME.getTransactionCategory() to VavilonTheme.colors.savings2,
        TransactionCategories.INCOME.getTransactionCategory() to VavilonTheme.colors.income2,
        TransactionCategories.CUSTOM.getTransactionCategory() to VavilonTheme.colors.savings3
    )
    val transactionCounts = transactionState.transactionList.groupingBy { it.category }.eachCount()
    val maxTransactionCount = transactionCounts.values.maxOrNull() ?: 0
    val barWidth = 40.dp
    val spacing = 10.dp
    val canvasHeight = 200.dp
    val canvasWidth = (transactionCounts.size * (barWidth + spacing))
    Row (Modifier
        .fillMaxWidth()
        .padding(start = 5.dp, end = 10.dp, top = 5.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .width(canvasWidth)
                .padding(5.dp),
        ) {
            val barHeightRatio = size.height / (maxTransactionCount.toFloat())

            var currentX = 0f
            transactionCounts.forEach { (category, count) ->
                val barHeight = count * barHeightRatio
                val color = transactionColors[category] ?: Color.LightGray
                drawRect(
                    color = color,
                    topLeft = androidx.compose.ui.geometry.Offset(currentX, size.height - barHeight),
                    size = androidx.compose.ui.geometry.Size(barWidth.toPx(), barHeight)
                )
                currentX += barWidth.toPx() + spacing.toPx()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Legend(transactionCounts)
    }
}

@Composable
fun Legend(categories: Map<String, Int>) {
    val transactionColors = mapOf(
        TransactionCategories.RENT.getTransactionCategory() to VavilonTheme.colors.expense1,
        TransactionCategories.FOOD.getTransactionCategory() to VavilonTheme.colors.expense2,
        TransactionCategories.TRAVEL.getTransactionCategory() to VavilonTheme.colors.expense3,
        TransactionCategories.TAXES.getTransactionCategory() to VavilonTheme.colors.expense4,
        TransactionCategories.HOBBY.getTransactionCategory() to VavilonTheme.colors.helpElement,
        TransactionCategories.RESTAURANTS.getTransactionCategory() to VavilonTheme.colors.income1,
        TransactionCategories.ONETIME.getTransactionCategory() to VavilonTheme.colors.savings2,
        TransactionCategories.INCOME.getTransactionCategory() to VavilonTheme.colors.income2,
        TransactionCategories.CUSTOM.getTransactionCategory() to VavilonTheme.colors.savings3
    )
    Column {
        categories.entries.forEach { category ->
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(16.dp),
                    color = transactionColors[category.key] ?: Color.Gray,
                    shape = RoundedCornerShape(2.dp)
                ) {}
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = category.key + " " + "(${category.value})",
                    style = Typography.body1.copy(color = VavilonTheme.colors.primaryText)
                )
            }
        }
    }
}

@Preview(widthDp = 400, heightDp = 200)
@Composable
private fun ChartPreview() {
    VavilonTheme {
        BarChartTransaction(
            transactionState = TransactionState(
                listOf(
                    Transaction(1005.0, "Income", Date().toString()),
                    Transaction(1805.0, "Food", Date().toString()),
                    Transaction(1005.0, "Rent", Date().toString()),
                    Transaction(1005.0, "Income", Date().toString())
                )
            )
        )
    }

}