package com.barisguneri.earthquakeapp.ui.features.map.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.PreviewMockData.mockEarthquakeInfo
import com.barisguneri.earthquakeapp.core.common.convertLongToDateString
import com.barisguneri.earthquakeapp.core.common.toReadableString
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens

@Composable
fun HeaderDetailContent(modifier: Modifier, earthquake: EarthquakeDetail) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailItem(
                modifier = Modifier.weight(1f),
                headerName = R.string.dateTime,
                detailText = convertLongToDateString(earthquake.dateTime)
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimens.dp1),
                color = colors.background.copy(alpha = 0.5f)
            )
            DetailItem(
                modifier = Modifier.weight(1f),
                headerName = R.string.city,
                detailText = earthquake.epiCenter.name
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailItem(
                modifier = Modifier.weight(1f),
                headerName = R.string.magnitude,
                detailText = earthquake.magnitude.toString()
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimens.dp1),
                color = colors.background.copy(alpha = 0.5f)
            )
            DetailItem(
                modifier = Modifier.weight(1f),
                headerName = R.string.population,
                detailText = earthquake.epiCenter.population.toReadableString()
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailItem(
                modifier = Modifier.weight(1f),
                headerName = R.string.depth,
                detailText = earthquake.depthInfo
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimens.dp1),
                color = colors.background.copy(alpha = 0.5f)
            )
            DetailItem(
                modifier = Modifier.weight(1f),
                headerName = R.string.cityCode,
                detailText = earthquake.epiCenter.code.toString()
            )
        }
    }
}

@Composable
private fun DetailItem(
    headerName: Int,
    detailText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = dimens.dp16, vertical = dimens.dp12),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = headerName),
            style = AppTheme.typography.bodyMediumNormal(),
            color = colors.text.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimens.dp4))
        Text(
            text = detailText,
            style = AppTheme.typography.titleMediumMedium(),
            color = colors.text,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun HeaderDetailContentPreview() {
    AppTheme {
        HeaderDetailContent(modifier = Modifier, earthquake = mockEarthquakeInfo)
    }

}