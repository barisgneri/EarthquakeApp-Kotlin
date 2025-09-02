package com.barisguneri.earthquakeapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.ui.navigation.graph.RootNavGraph
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUserAgentForOSM()

        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}

private fun setUserAgentForOSM(){
    Configuration.getInstance().userAgentValue = BuildConfig.LIBRARY_PACKAGE_NAME
}


