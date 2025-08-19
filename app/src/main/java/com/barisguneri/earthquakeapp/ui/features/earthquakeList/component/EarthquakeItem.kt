package com.barisguneri.earthquakeapp.ui.features.earthquakeList.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.Location

@Composable
fun EarthquakeItem(earthquake: EarthquakeInfo, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.background(Color.White).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "%.1f".format(earthquake.magnitude),
                style = MaterialTheme.typography.headlineSmall,
                color = when {
                    earthquake.magnitude >= 5.0 -> MaterialTheme.colorScheme.error
                    earthquake.magnitude >= 3.0 -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = earthquake.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "${earthquake.date} - ${earthquake.dateTime}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "${earthquake.depthInfo} - ${earthquake.magnitude} - ${earthquake.location.lat} - ${earthquake.location.long}" , style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        HorizontalDivider(color = Color.Black, thickness = 1.dp)
    }
}

@Preview
@Composable
fun EarthquakeItemPreview(){
    val earthquake = EarthquakeInfo(
        id = "",
        location = Location(lat = 1344.2, long = 1234.1),
        date = "12/12/2012",
        dateTime = "12/12/2012 12:12",
        depthInfo = "40km",
        magnitude = 3.4,
        title = "Silivri Açıkları"
    )
    val earthquake1 = EarthquakeInfo(
        id = "",
        location = Location(lat = 1344.2, long = 1234.1),
        date = "12/12/2012",
        dateTime = "12/12/2012 12:12",
        depthInfo = "40km",
        magnitude = 3.4,
        title = "Silivri Açıkları"
    )
    val list = listOf(earthquake, earthquake1)
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(list.size) { index ->
                EarthquakeItem(list[index]) { }
            }
        }
    )
}