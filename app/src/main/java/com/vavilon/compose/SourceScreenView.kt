package com.vavilon.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vavilon.compose.chart.PieChart
import com.vavilon.compose.menu.BottomNavigation
import com.vavilon.compose.source.EditSourceScreen
import com.vavilon.compose.source.SourceCategoryRowView
import com.vavilon.compose.source.SourceListView
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.states.SourceState
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun SourceScreenView(
    modifier: Modifier,
    state: SourceState,
    navController: NavController,
    onEvent: (SourceEvent) -> Unit
) {
    if (state.isEditingSource) {
        Log.d("Source to edit", "Current Source: ${state.sourceId}")
        EditSourceScreen(state = state, onEvent = onEvent)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(VavilonTheme.colors.backgroundUI),
        bottomBar = {
            BottomNavigation(
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(VavilonTheme.colors.backgroundUI)
                .padding(innerPadding)
        ) {
            PieChart(state = state)
            Spacer(modifier = Modifier.height(5.dp))
            SourceCategoryRowView(onEvent = onEvent)
            Spacer(modifier = Modifier.height(5.dp))
            SourceListView(state = state, onEvent = onEvent)
        }
    }
}

@Preview(showBackground = true, heightDp = 600, widthDp = 350)
@Composable
private fun SourceScreenPreview() {
    VavilonTheme {
        val navController = rememberNavController()
        SourceScreenView(
            modifier = Modifier.fillMaxSize(),
            state = SourceState(),
            navController = navController
        ) {
        }
    }

}