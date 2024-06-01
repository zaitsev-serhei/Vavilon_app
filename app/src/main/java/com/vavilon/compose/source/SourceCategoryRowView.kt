package com.vavilon.compose.source

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.ui.theme.DeepWater
import com.vavilon.ui.theme.Gold
import com.vavilon.ui.theme.Typography

@Composable
fun SourceCategoryRowView(onEvent: (SourceEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,) {
        SourceCategories.entries.forEach { category ->
            Row(modifier = Modifier
                .background(DeepWater)
                .padding(5.dp)
                .clickable {
                onEvent(SourceEvent.FilterSource(category))
                onEvent(SourceEvent.SetType(category))},
                verticalAlignment = CenterVertically){
                Text(text = category.getSrcCategory(),
                    style = Typography.body1.copy(color = Gold))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 150)
@Composable
private fun PreviewRow() {
    SourceCategoryRowView(onEvent = {} )
}