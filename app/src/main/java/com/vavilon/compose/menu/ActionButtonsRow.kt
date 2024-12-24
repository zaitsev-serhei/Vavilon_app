package com.vavilon.compose.menu

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.R
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.events.UserEvent
import com.vavilon.ui.theme.Typography
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun ActionButtonsRow(
    onAddSource:() -> Unit,
    onAddTransaction:() -> Unit
) {
    val context = LocalContext.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier
            .size(90.dp)
            .padding(5.dp)
            .clickable {
                onAddSource()
            }
            ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_add_button),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp))
                Text(text = stringResource(id = R.string.new_source),
                    style = Typography.body2.copy(VavilonTheme.colors.darkText) )

            }

        }
        Card(modifier = Modifier
            .size(90.dp)
            .padding(5.dp)
            .clickable {
                onAddTransaction()
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_transaction_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp))
                Text(text = stringResource(id = R.string.new_transaction),
                    style = Typography.body2.copy(VavilonTheme.colors.darkText) )

            }

        }
        Card(modifier = Modifier
            .size(90.dp)
            .padding(5.dp)
            .clickable {
                Toast
                    .makeText(
                        context,
                        "To be implemented",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_bitcoin_sign),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp))
                Text(text = stringResource(id = R.string.crypto),
                    style = Typography.body2.copy(VavilonTheme.colors.darkText) )

            }

        }
    }
}

@Preview
@Composable
private fun PreviewActionRow() {
    VavilonTheme {
        ActionButtonsRow(onAddTransaction = {}, onAddSource = {})
    }
}