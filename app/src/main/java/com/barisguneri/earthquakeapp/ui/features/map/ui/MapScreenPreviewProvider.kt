package com.barisguneri.earthquakeapp.ui.features.map.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.barisguneri.earthquakeapp.core.data.ErrorType
import com.barisguneri.earthquakeapp.ui.main.SharedContract

class MapScreenPreviewProvider : PreviewParameterProvider<SharedContract.UiState> {
    override val values: Sequence<SharedContract.UiState>
        get() = sequenceOf(
            SharedContract.UiState(
                isLoading = true,
                error = null,
            ),
            SharedContract.UiState(
                isLoading = false,
                error = ErrorType.TooManyRequests,
            ),
            SharedContract.UiState(
                isLoading = false,
                error = ErrorType.HttpError(code = 404, "Bilinmeyen Hata"),
            ),
            SharedContract.UiState(
                isLoading = false,
                error = null,
            )

        )

}
