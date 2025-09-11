package com.barisguneri.earthquakeapp.ui.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.primaryBlue
import com.barisguneri.earthquakeapp.ui.theme.secondaryWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navActions: SplashNavActions) {

    LaunchedEffect(key1 = true) {
        delay(2500L)
        navActions.navigateToHome()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(id = R.drawable.earthquake_logo),
            contentDescription = "Uygulama Logosu",
            modifier = Modifier.size(200.dp)
        )
    }
}