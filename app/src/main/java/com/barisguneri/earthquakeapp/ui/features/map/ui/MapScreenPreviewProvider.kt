package com.barisguneri.earthquakeapp.ui.features.map.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.barisguneri.earthquakeapp.core.data.ErrorType
import com.barisguneri.earthquakeapp.core.data.PreviewMockData
import com.barisguneri.earthquakeapp.ui.features.map.viewmodel.MapContract

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
                earthquake = PreviewMockData.earthquakeInfoModel
            )

        )

}
