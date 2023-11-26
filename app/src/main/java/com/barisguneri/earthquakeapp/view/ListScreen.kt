package com.barisguneri.earthquakeapp.view

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.barisguneri.earthquakeapp.MAP_SCREEN
import com.barisguneri.earthquakeapp.model.Earthquake
import com.barisguneri.earthquakeapp.model.Result
import com.barisguneri.earthquakeapp.viewmodel.EarthquakeViewModel
import kotlinx.coroutines.launch

@Composable
fun ListScreen(navController: NavHostController, viewModel: EarthquakeViewModel){
    val earthquakeList by remember {viewModel.earthquakeList}
    println(earthquakeList)
    EarthquakeListView(earthquake = earthquakeList, navController, viewModel = viewModel)
}

@Composable
fun EarthquakeListView(earthquake: List<Result>, navController: NavHostController, viewModel: EarthquakeViewModel){
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(earthquake) { earthquakes ->
            EarthquakeListItem(navController, earthquake = earthquakes, viewModel)
        }
    }
}
@Composable
fun EarthquakeListItem(navController: NavHostController, earthquake: Result, viewModel: EarthquakeViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                viewModel.setEarthquake(earthquake)
                navController.navigate(MAP_SCREEN)
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = earthquake.title,
                style = TextStyle(),
                modifier = Modifier.padding(6.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            Text(text = earthquake.date,
                modifier = Modifier.padding(6.dp))
        }
        Text(text = "${earthquake.mag}",
            color = magColor(earthquake.mag))
    }
}
private fun magColor(mag: Double) : Color{
    return if (mag > 4 && mag < 6.0){
        Color.Yellow
    }else if(mag > 6.0){
        Color.Red
    }else{
        Color.Black
    }
}
