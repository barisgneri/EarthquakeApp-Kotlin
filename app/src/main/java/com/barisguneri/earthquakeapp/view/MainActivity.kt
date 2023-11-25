package com.barisguneri.earthquakeapp.view

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.barisguneri.earthquakeapp.ui.theme.EarthquakeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EarthquakeAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "list_screen", builder ={
                    composable("list_screen"){
                        ListScreen(navController = navController, context = this@MainActivity)
                    }
                    composable("map_screen/{latitude}/{longitude}", arguments = listOf(
                        navArgument("latitude"){
                            type = NavType.StringType
                        },
                        navArgument("longitude"){
                            type = NavType.StringType
                        }
                    )){
                        val latitude = remember {
                            it.arguments?.getString("latitude")
                        }

                        val longitude = remember {
                            it.arguments?.getString("longitude")
                        }

                        if (latitude != null && longitude != null){
                            MapViewScreen(latitude, longitude, navController)
                        }

                    }
                })
            }
            val ctx = applicationContext
            Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
            Configuration.getInstance().userAgentValue = "MapApp"
        }
    }
}


