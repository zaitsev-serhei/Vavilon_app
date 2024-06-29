package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
    val textColor = when (SourceCategories.entries.firstOrNull { category ->
        category.getSrcCategory() == source.sourceType
    } ?: SourceCategories.INCOME) {
        SourceCategories.INCOME -> VavilonTheme.colors.helpText
        SourceCategories.EXPENSE -> VavilonTheme.colors.darkText
        SourceCategories.SAVING -> VavilonTheme.colors.primaryText
    }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(110.dp)
            .shadow(4.dp,
                shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = itemBackGroundColor
    ) {
        Row(
            Modifier
                .padding(start = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_default),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .background(VavilonTheme.colors.backgroundIcon, shape = CircleShape)
                    .padding(5.dp),
                tint = VavilonTheme.colors.secondaryElement
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = source.sourceTitle,
                    style = Typography.h2,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${source.currentBalance} USD",
                    style = Typography.body2,
                    color = textColor
                )
            }
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
@Preview
@Composable
private fun CardItem2() {
    VavilonTheme {
        SourceRowItemView(source = Source("Saving", "work", "", 1100.0))
    }
}