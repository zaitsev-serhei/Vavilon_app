package com.vavilon.compose.source

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Midnight

@Composable
fun SourceListItemView(source: Source,
                       onEvent: (SourceEvent) -> Unit
                       ) {
    Row (modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Image(painter = painterResource(id = R.drawable.ic_user_default ),
            contentDescription = null,
            Modifier.size(50.dp)
        )
        Column(modifier = Modifier
            .padding(start = 10.dp)
            .weight(1F)
        ) {
            Text(text = source.sourceTitle)
            Text(text = source.sourceDescription)
        }
        Text(text = source.currentBalance.toString(),
            Modifier.padding(start = 5.dp, end = 10.dp))
        Row (Modifier
            .padding( 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Image(painter = painterResource(id = R.drawable.ic_add_button),
                contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem() {
    SourceListItemView(source = Source("Income","Job", "Job I love very much", 1250.0),
        onEvent = {} )
}

@Preview(showBackground = true)
@Composable
private fun PreviewListItem1() {
    SourceListItemView(source = Source("Income","Job", "test", 1250.0),
        onEvent = {} )
}