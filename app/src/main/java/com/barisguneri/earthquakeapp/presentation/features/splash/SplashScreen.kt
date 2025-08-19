package com.barisguneri.earthquakeapp.presentation.features.splash

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
import com.barisguneri.earthquakeapp.ui.theme.primaryBlue
import com.barisguneri.earthquakeapp.ui.theme.secondaryWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToHome: () -> Unit) {

    // Dalgalanma efekti için fırça (Brush) oluşturma
    val brush = Brush.linearGradient(
        colors = listOf(primaryBlue, secondaryWhite.copy(1f)),
        start = androidx.compose.ui.geometry.Offset(900f, 900f),
        end = androidx.compose.ui.geometry.Offset(900f, 5f)
    )

    LaunchedEffect(key1 = true) {
        delay(3000L)
        navigateToHome()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(id = R.drawable.earthquake_logo),
            contentDescription = "Uygulama Logosu",
            modifier = Modifier.size(200.dp)
        )
    }
}