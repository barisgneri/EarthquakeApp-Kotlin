package com.barisguneri.earthquakeapp.ui.features.detail.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.core.common.PreviewMockData
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract

class EarthquakeDetailScreenPreviewProvider : PreviewParameterProvider<EarthquakeDetailContract.UiState>{
    override val values: Sequence<EarthquakeDetailContract.UiState>
        get() = sequenceOf(
            EarthquakeDetailContract.UiState(
                isLoading = true,
                earthquake = null,
                error = null
            ),
            EarthquakeDetailContract.UiState(
                isLoading = false,
                earthquake = null,
                error = ErrorType.HttpError(404, "Bilinmeyen hata oluştu")
            ),
            EarthquakeDetailContract.UiState(
                isLoading = false,
                error = null,
                earthquake = PreviewMockData.mockEarthquakeInfo
            )
        )

}
