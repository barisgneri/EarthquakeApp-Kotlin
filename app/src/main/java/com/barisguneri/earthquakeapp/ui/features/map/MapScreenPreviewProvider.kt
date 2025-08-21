package com.barisguneri.earthquakeapp.ui.features.map

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.Location

class MapScreenPreviewProvider : PreviewParameterProvider<MapContract.UiState> {
    override val values: Sequence<MapContract.UiState>
        get() = sequenceOf(
            MapContract.UiState(
                isLoading = true,
                error = null,
                earthquake = null
            ),
            MapContract.UiState(
                isLoading = false,
                error = ErrorType.TooManyRequests,
                earthquake = null
            ),
            MapContract.UiState(
                isLoading = false,
                error = ErrorType.HttpError(code = 404, "Bilinmeyen Hata"),
                earthquake = null
            ),
            MapContract.UiState(
                isLoading = false,
                error = null,
                earthquake = listOf(
                    EarthquakeInfo(
                        id = "1", location = Location(lat = 1.0, long = 2.0), magnitude = 5.0,
                        date = "02.03.1999",
                        dateTime = "02.03.1999 12:12:12",
                        depthInfo = "4 km",
                        title = "Test"
                    )
                )
            )

        )

}
