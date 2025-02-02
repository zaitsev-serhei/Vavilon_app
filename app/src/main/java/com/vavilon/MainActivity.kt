package com.vavilon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vavilon.model.events.UserEvent
import com.vavilon.ui.theme.VavilonTheme
import com.vavilon.viewModel.PlanViewModel
import com.vavilon.viewModel.SourceViewModel
import com.vavilon.viewModel.TransactionViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sourceViewModel: SourceViewModel
    @Inject
    lateinit var transactionViewModel: TransactionViewModel
    @Inject
    lateinit var planViewModel: PlanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as VavilonApplication).appComponent.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContent {
            VavilonTheme {
                val sourceState by sourceViewModel.state.collectAsState()
                val transactionState by transactionViewModel.state.collectAsState()
                val planState by planViewModel.state.collectAsState()

                val onEvent: (UserEvent) -> Unit = { event ->
                    when (event) {
                        is UserEvent.SourceEventWrapper -> sourceViewModel.onEvent(event.event)
                        is UserEvent.TransactionEventWrapper -> transactionViewModel.onEvent(event.event)
                        is UserEvent.PlanEventWrapper -> planViewModel.OnEvent(event.event)
                    }
                }
                Navigation(modifier = Modifier.fillMaxSize(),
                    sourceState = sourceState,
                    transactionState = transactionState,
                    planState = planState,
                    onEvent = onEvent,
                    )
            }
        }
    }
}




