package com.barisguneri.earthquakeapp.ui.features.map.component

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.barisguneri.earthquakeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenAppBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.map),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }, modifier = modifier.background(color = Color.White))
}