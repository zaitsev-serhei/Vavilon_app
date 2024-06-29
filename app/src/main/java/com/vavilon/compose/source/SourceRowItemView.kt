package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.SourceCategories
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun SourceRowItemView(source: Source) {
    val itemBackGroundColor = when (SourceCategories.entries.firstOrNull { category ->
        category.getSrcCategory() == source.sourceType
    } ?: SourceCategories.INCOME) {
        SourceCategories.INCOME -> VavilonTheme.colors.income2
        SourceCategories.EXPENSE -> VavilonTheme.colors.expense3
        SourceCategories.SAVING -> VavilonTheme.colors.savings2
    }
    Card(
        Modifier
            .size(100.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            Modifier
                .background(itemBackGroundColor)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_default),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 5.dp),
                tint = VavilonTheme.colors.secondaryElement
            )
            Text(
                text = source.sourceTitle,
                style = Typography.h2,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CardItem() {
    VavilonTheme {
        SourceRowItemView(source = Source("Income", "work", "", 1100.0))
    }
}

@Preview
@Composable
private fun CardItem1() {
    VavilonTheme {
        SourceRowItemView(source = Source("Expense", "work", "", 1100.0))
    }
}