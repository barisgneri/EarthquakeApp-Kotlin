package com.barisguneri.earthquakeapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.presentation.navigation.graph.RootNavGraph
import com.barisguneri.earthquakeapp.ui.theme.EarthquakeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EarthquakeAppTheme {
                val navController = rememberNavController()

                RootNavGraph(navController = navController)
            }
        }
    }
}


