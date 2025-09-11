package com.barisguneri.earthquakeapp.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.icons
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun ErrorView(
    errorType: ErrorType,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val message = when (errorType) {
        is ErrorType.NoInternetConnection -> "İnternet bağlantınızı kontrol edin."
        is ErrorType.HttpError -> "Sunucuya ulaşırken bir sorun oluştu. (Kod: ${errorType.code})"
        else -> "Beklenmedik bir hata oluştu. Lütfen tekrar deneyin."
    }
    Column(
        modifier = modifier
            .background(color = colors.background)
            .padding(padding.dimension12),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = icons.errorIcon, contentDescription = "Hata", modifier = Modifier.size(
                dimens.dp55
            )
        )
        Text(
            modifier = Modifier.padding(top = padding.dimension12),
            text = stringResource(R.string.something_went_wrong),
            style = MaterialTheme.typography.titleLarge,
            color = colors.text,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(
                top = padding.dimension8,
            ),
            textAlign = TextAlign.Center,
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal
        )
        Button(
            modifier = Modifier.padding(top = padding.dimension12),
            colors = ButtonDefaults.buttonColors(containerColor = colors.button),
            shape = RoundedCornerShape(dimens.dp8),
            onClick = onRetry
        ) {
            Text(
                text = stringResource(R.string.try_again),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.white,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    AppTheme {
        ErrorView(
            errorType = ErrorType.HttpError(code = 500, "Sunucu hatası"),
            onRetry = {}
        )
    }
}