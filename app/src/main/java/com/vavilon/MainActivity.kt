package com.vavilon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vavilon.compose.HomeScreenView
import com.vavilon.ui.theme.VavilonTheme
import com.vavilon.viewModel.SourceViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sourceViewModel: SourceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as VavilonApplication).appComponent.injectMainActivity(this)
       // enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            VavilonTheme {
                val state by sourceViewModel.state.collectAsState()
                Navigation(modifier = Modifier.fillMaxSize(),
                    sourceState = state,
                    onEvent = sourceViewModel::onEvent)
            }
        }
    }
}




