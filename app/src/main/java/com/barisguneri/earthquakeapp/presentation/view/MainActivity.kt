package com.barisguneri.earthquakeapp.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.barisguneri.earthquakeapp.presentation.navigation.ScreenNavigation
import com.barisguneri.earthquakeapp.ui.theme.EarthquakeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EarthquakeAppTheme {

                    ScreenNavigation()

            }
        }
    }
}


