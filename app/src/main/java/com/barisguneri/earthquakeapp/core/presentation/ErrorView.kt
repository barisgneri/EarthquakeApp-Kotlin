package com.barisguneri.earthquakeapp.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.common.ErrorType

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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Tekrar Dene")
        }
    }
}