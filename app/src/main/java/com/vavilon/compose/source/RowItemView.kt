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
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.BlueGreen
import com.vavilon.ui.theme.DeepWater
import com.vavilon.ui.theme.Typography

@Composable
fun SourceRowItemView(source:Source) {
    Card(Modifier
        .size(100.dp)
        .wrapContentSize(Alignment.Center)
    ){
        Column (Modifier
            .background(BlueGreen)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            Icon(painter = painterResource(id = R.drawable.ic_user_default),
                contentDescription = null,
                modifier = Modifier.size(70.dp).padding(top = 5.dp),
                tint = DeepWater)
            Text(text = source.sourceTitle,
                style = Typography.h2,
                modifier = Modifier.padding(bottom = 10.dp))
        }

    }
}

@Preview
@Composable
private fun CardItem() {
    SourceRowItemView(source = Source("temp", "work", "",1100.0))
}
