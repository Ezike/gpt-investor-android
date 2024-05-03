package com.thejawnpaul.gptinvestor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.thejawnpaul.gptinvestor.navigation.AppNavigator
import com.thejawnpaul.gptinvestor.theme.GPTInvestorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPTInvestorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val navHostController = rememberNavController()
                    val pageHolder = remember(navHostController) {
                        PageHolder(
                            pageRegistry = PageRegistry(),
                            navHostController = navHostController,
                            navigator = AppNavigator(coroutineScope),
                        )
                    }
                    pageHolder.UI()
                }
            }
        }
    }
}