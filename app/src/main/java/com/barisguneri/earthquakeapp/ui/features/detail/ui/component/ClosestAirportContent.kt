package com.barisguneri.earthquakeapp.ui.features.detail.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.PreviewMockData
import com.barisguneri.earthquakeapp.core.common.toKmFormattedString
import com.barisguneri.earthquakeapp.domain.model.Airports
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.fontSize
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun ClosestAirportContent(modifier: Modifier = Modifier, airports: List<Airports>) {

    CollapsedDetailItem(modifier = modifier, title = stringResource(R.string.closestAirport)) {
        Column {
            airports.forEach { airportItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = padding.dimension8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            color = colors.text,
                            text = airportItem.code,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFontFamily,
                            fontSize = fontSize.mediumSmall
                        )
                        Text(
                            color = colors.text,
                            text = airportItem.name,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            fontSize = fontSize.mediumSmall
                        )
                    }
                    Text(
                        color = colors.text,
                        text = airportItem.distance.toKmFormattedString(),
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFontFamily,
                        fontSize = fontSize.mediumSmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ClosestAirportContentPreview() {
    ClosestAirportContent(airports = PreviewMockData.mockEarthquakeInfo.airports)
}