package com.barisguneri.earthquakeapp.presentation.navigation.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.ui.theme.EarthquakeAppTheme

@Composable
fun EarthquakeBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomNavItems = remember { BottomNavigationItems.getItems() }

    Box(
        modifier = Modifier.background(color = Color.Gray)
    ) {
        NavigationBar(
            modifier = modifier.background(color = Color.Gray),
            containerColor = Color.DarkGray,
            contentColor = Color.Black
        ) {
            bottomNavItems.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(screen.icon),
                            contentDescription = screen.name,
                            tint = if (selected) Color.Green else Color.Red,
                            modifier = Modifier.height(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = screen.name,
                            color = Color.Black,
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigateReorderBackStack(screen.route)
                    },
                    enabled = !selected,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Green,
                        unselectedIconColor = Color.White,
                        indicatorColor = Color.Transparent
                    ),
                    alwaysShowLabel = true
                )
            }
        }
        HorizontalDivider(
            modifier = modifier
                .fillMaxWidth().align(Alignment.TopCenter),
            thickness = 1.dp,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    EarthquakeAppTheme {
        EarthquakeBottomBar(navController = rememberNavController())
    }
}