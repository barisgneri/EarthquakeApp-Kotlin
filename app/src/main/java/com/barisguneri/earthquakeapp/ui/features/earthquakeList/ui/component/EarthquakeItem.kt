package com.barisguneri.earthquakeapp.ui.features.earthquakeList.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.core.common.calculateNowAndDateTimeBetween
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.Location
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.fontSize
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun EarthquakeItem(earthquake: EarthquakeInfo, onClick: () -> Unit) {

    val magnitudeBackgroundColor = when {
        earthquake.magnitude >= 5.0 -> colors.magnitudeBackgroundRed
        earthquake.magnitude >= 4.5 && earthquake.magnitude < 5.0 -> colors.magnitudeBackgroundOrange
        earthquake.magnitude >= 3.5 && earthquake.magnitude < 4.4 -> colors.magnitudeBackgroundYellow
        earthquake.magnitude < 3.5 -> colors.magnitudeBackgroundGreen
        else -> colors.magnitudeBackgroundGreen
    }
    val magnitudeTitleColor = when {
        earthquake.magnitude >= 5.0 -> colors.magnitudeTextRed
        earthquake.magnitude >= 4.5 && earthquake.magnitude < 5.0 -> colors.magnitudeTextOrange
        earthquake.magnitude >= 3.5 && earthquake.magnitude < 4.4 -> colors.magnitudeTextYellow
        earthquake.magnitude < 3.5 -> colors.magnitudeTextGreen
        else -> colors.magnitudeTextGreen
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(padding.dimension16),
        modifier = Modifier
            .background(colors.background)
            .padding(padding.dimension12)
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Text(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    magnitudeBackgroundColor.copy(alpha = 0.5f)
                )
                .defaultMinSize(minWidth = dimens.dp55)
                .padding(
                    padding.dimension12
                ),
            textAlign = TextAlign.Center,
            text = "%.1f".format(earthquake.magnitude),
            color = magnitudeTitleColor,
            fontFamily = poppinsFontFamily,
            fontSize = fontSize.large,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = earthquake.title,
                fontFamily = poppinsFontFamily,
                color = colors.text,
                fontSize = fontSize.medium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = calculateNowAndDateTimeBetween(context = LocalContext.current, timestamp = earthquake.dateTime),
                color = colors.textSecondary,
                fontSize = fontSize.mediumSmall,
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily,
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = colors.onBackground,
        thickness = 0.6.dp
    )
}

@Preview
@Composable
fun EarthquakeItemPreview() {
    val earthquake = EarthquakeInfo(
        id = "",
        location = Location(lat = 1344.2, lng = 1234.1),
        date = "12/12/2012",
        dateTime = 1,
        depthInfo = "40km",
        magnitude = 1.1,
        title = "Silivri Açıkları, Silivri Açıkları, Silivri Açıkları"
    )
    val earthquake1 = EarthquakeInfo(
        id = "",
        location = Location(lat = 1344.2, lng = 1234.1),
        date = "12/12/2012",
        dateTime = 1,
        depthInfo = "40km",
        magnitude = 3.8,
        title = "Silivri Açıkları"
    )
    val earthquake3 = EarthquakeInfo(
        id = "",
        location = Location(lat = 1344.2, lng = 1234.1),
        date = "12/12/2012",
        dateTime = 1,
        depthInfo = "40km",
        magnitude = 4.5,
        title = "Silivri Açıkları"
    )
    val earthquake4 = EarthquakeInfo(
        id = "",
        location = Location(lat = 1344.2, lng = 1234.1),
        date = "12/12/2012",
        dateTime = 1,
        depthInfo = "40km",
        magnitude = 5.4,
        title = "Silivri Açıkları"
    )
    val list = listOf(earthquake, earthquake1, earthquake3, earthquake4)
    AppTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                items(list.size) { index ->

                    EarthquakeItem(list[index]) { }
                }
            }
        )
    }

}