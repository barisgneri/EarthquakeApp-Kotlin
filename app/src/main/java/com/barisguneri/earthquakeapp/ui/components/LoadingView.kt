package com.barisguneri.earthquakeapp.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(color = colors.background)
            .padding(padding.dimension12),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(padding.dimension12)
                .size(size = dimens.dp80),
            color = colors.primary,
            trackColor = colors.primary.copy(alpha = 0.3f),
            strokeWidth = dimens.dp4
        )
        Text(
            modifier = Modifier.padding(
                top = padding.dimension8,
            ),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.loading_earthquake_data),
            style = MaterialTheme.typography.bodyLarge,
            color = colors.text,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
fun LoadingViewPreview() {
    AppTheme {
        LoadingView(modifier = Modifier)
    }
}