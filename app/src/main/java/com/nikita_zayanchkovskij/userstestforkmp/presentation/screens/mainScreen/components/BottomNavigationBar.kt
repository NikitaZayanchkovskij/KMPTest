package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.mainScreen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFBlue

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val tabsList = listOf(
        BottomNavTabs.HomeTab,
        BottomNavTabs.BookmarksTab
    )

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = IDFBlue
    ) {
        /* Это нужно, чтобы была возможность отметить активную выбранную вкладку в
         * BottomNavigationBar-е.
         */
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentDestination = navBackStackEntry?.destination

        tabsList.forEach { bottomNavTab ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == bottomNavTab.route::class.qualifiedName
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(bottomNavTab.route)
                },
                icon = {
                    Icon(
                        painter = if (isSelected) {
                            painterResource(bottomNavTab.selectedIconId)
                        } else {
                            painterResource(bottomNavTab.unSelectedIconId)
                        },
                        contentDescription = "Tab Icon"
                    )
                },
                label = {
                    Text(text = bottomNavTab.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = IDFBlue,
                    selectedTextColor = IDFBlue,
                    indicatorColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline
                ),
                alwaysShowLabel = true
            )
        }
    }
}