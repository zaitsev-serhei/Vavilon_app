package com.vavilon.compose.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.vavilon.R
import com.vavilon.model.states.SourceState
import com.vavilon.model.states.TransactionState
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun HomeStatisticMenu(
    sourceState: SourceState,
   // transactionState: TransactionState,
    ) 
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(VavilonTheme.colors.backgroundUI),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card {
            Column {
                Row {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = null)
                    Text(text = "Income")
                    Text(text = sourceState.sourceList.stream().count().toString())
                }

            }
        }
        Card {
            Column {
                Row {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = null)
                    Text(text = "Income")
                    Text(text = sourceState.sourceList.stream().count().toString())
                }

            }
        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 250)
@Composable
private fun PreviewManu() {
    VavilonTheme {
        HomeStatisticMenu(sourceState = SourceState())//, transactionState = TransactionState() )
    }

}