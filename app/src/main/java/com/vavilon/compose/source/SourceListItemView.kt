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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.events.SourceEvent
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun SourceListItemView(
    source: Source,
    modifier: Modifier,
    onEvent: (SourceEvent) -> Unit
) {
    //val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
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
            Text(text = source.sourceTitle)
            Text(text = source.sourceDescription)
        }
        Text(
            text = source.currentBalance.toString(),
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
                        onEvent(SourceEvent.EditSource(source))
                    })
            Spacer(modifier = Modifier.width(10.dp))
            Icon(painter = painterResource(id = R.drawable.ic_trash_can),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onEvent(SourceEvent.DeleteSource(source))
                    })
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem() {
    VavilonTheme {
        SourceListItemView(
            source = Source("Income", "Job", "Job I love very much", 1250.0),
            onEvent = {}, modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem1() {
    VavilonTheme {
        SourceListItemView(
            source = Source("Income", "Job", "test", 1250.0),
            onEvent = {}, modifier = Modifier
        )
    }
}