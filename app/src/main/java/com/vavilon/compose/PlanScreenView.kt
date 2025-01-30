package com.vavilon.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vavilon.compose.chart.PieChartSources
import com.vavilon.compose.menu.BottomNavigation
import com.vavilon.compose.source.SourceCategoryRowView
import com.vavilon.compose.source.SourceListView
import com.vavilon.model.events.UserEvent
import com.vavilon.model.states.PlanState
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun PlanScreeView(
    modifier: Modifier,
    planState: PlanState,
    navController:NavController,
    onEvent:(UserEvent) -> Unit
) {
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
            //screen content

        }
    }
}