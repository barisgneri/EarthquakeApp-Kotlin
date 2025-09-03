package com.barisguneri.earthquakeapp.ui.navigation.bottom

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens

@Composable
fun EarthquakeBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomNavItems = remember { BottomNavigationItems.getItems() }

    Box{
        NavigationBar(
            containerColor = colors.background,
        ) {
            bottomNavItems.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(screen.icon),
                            contentDescription = stringResource(id = screen.name),
                            tint = if (selected) colors.primaryBlue else colors.text,
                            modifier = Modifier.height(26.dp),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.name),
                            color = if (selected) colors.primaryBlue else colors.text,
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigateReorderBackStack(screen.route)
                    },
                    enabled = !selected,
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                )
            }
        }
        HorizontalDivider(
            modifier = modifier
                .fillMaxWidth().align(Alignment.TopCenter),
            thickness = 0.6.dp,
            color = colors.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    AppTheme {
        EarthquakeBottomBar(navController = rememberNavController())
    }
}