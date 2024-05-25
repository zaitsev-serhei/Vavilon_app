package com.vavilon.compose.source

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Bronze
import com.vavilon.ui.theme.Gold
import com.vavilon.ui.theme.Midnight

@Composable
fun SourceRowItemView(source:Source) {
    Card(Modifier
        .border(1.dp, Bronze)
        .background(Midnight)
        .size(100.dp)
        .wrapContentSize(Alignment.Center)
    ){
        Column (Modifier
            .background(Midnight)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            Icon(painter = painterResource(id = R.drawable.ic_user_default),
                contentDescription = null,
                modifier = Modifier.size(70.dp).padding(top = 5.dp),
                tint = Bronze)
            Text(text = source.sourceTitle.toString(), color = Gold,
                modifier = Modifier.padding(bottom = 10.dp))
        }

    }
}

@Preview
@Composable
private fun CardItem() {
    SourceRowItemView(source = Source("temp", "work", "",1100.0))
}
