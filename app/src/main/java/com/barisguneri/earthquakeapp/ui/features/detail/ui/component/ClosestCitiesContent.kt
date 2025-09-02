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
import com.barisguneri.earthquakeapp.core.common.toReadableString
import com.barisguneri.earthquakeapp.domain.model.ClosestCities
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.fontSize
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun ClosestCitiesContent(modifier: Modifier = Modifier, closestCities: List<ClosestCities>) {

    CollapsedDetailItem(modifier = modifier, title = stringResource(R.string.closestCities)) {
        Column {
            closestCities.forEach { cityItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = padding.dimension8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            color = colors.text,
                            text = cityItem.name,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFontFamily,
                            fontSize = fontSize.mediumSmall
                        )
                        Text(
                            color = colors.text,
                            text = "Nüfus: ${cityItem.population.toReadableString()}",
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            fontSize = fontSize.mediumSmall
                        )
                    }
                    Text(
                        color = colors.text,
                        text = cityItem.distance.toKmFormattedString(),
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
fun ClosestCitiesContentPreview() {
    ClosestCitiesContent(closestCities = PreviewMockData.mockEarthquakeInfo.closestCities)
}
