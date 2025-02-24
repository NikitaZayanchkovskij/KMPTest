package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.mainScreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nikita_zayanchkovskij.userstestforkmp.presentation.navigation.BottomNavigationGraph
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.mainScreen.components.BottomNavigationBar

@Composable
fun MainScreen(
    mainScreensNavHostController: NavHostController
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { paddingValues ->
        BottomNavigationGraph(
            padding = paddingValues,
            bottomNavController = bottomNavController
        ) { detailedNewsItemDestination ->
            mainScreensNavHostController.navigate(detailedNewsItemDestination)
        }
    }
}