package com.barisguneri.earthquakeapp.ui.features.detail.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.icons
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.background(colors.background),
        title = {
            Text(
                text = stringResource(R.string.earthquake_detail),
                style = typography.bodyLargeBold()
            )
        },
        navigationIcon = {
            IconButton(
                content = { Icon(icons.backArrow, "backIcon", tint = colors.text, modifier = Modifier.size(36.dp)) },
                onClick = {
                    onBackClick()
                })
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}