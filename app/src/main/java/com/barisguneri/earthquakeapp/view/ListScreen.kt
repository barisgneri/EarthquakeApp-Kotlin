package com.barisguneri.earthquakeapp.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.barisguneri.earthquakeapp.model.Earthquake

class ListScreen {
}

@Composable
fun EarthquakeListItem(navController: NavController, earthquake: Earthquake) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .clickable {
                //Todo: Navigation eklenecek
            }
    ) {
        Text(text = "")
    }
}