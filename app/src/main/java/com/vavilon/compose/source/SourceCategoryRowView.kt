package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun SourceCategoryRowView(onEvent: (SourceEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(start = 15.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SourceCategories.entries.forEach { category ->
Card (modifier = Modifier
    .height(25.dp)
    .width(80.dp)
    .shadow(5.dp)
){
    Row(
        modifier = Modifier
            .background(VavilonTheme.colors.secondaryElement)
            .clickable {
                onEvent(SourceEvent.FilterSource(category))
                onEvent(SourceEvent.SetType(category))
            },
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = category.getSrcCategory(),
            color = VavilonTheme.colors.primaryText,
            maxLines = 1
        )
    }
}
}

    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 150)
@Composable
private fun PreviewRow() {
    VavilonTheme {
        SourceCategoryRowView(onEvent = {})
    }
}