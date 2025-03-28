package com.easternkite.recomposablewebview.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easternkite.recomposablewebview.ui.WebView

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    controller: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = controller,
        startDestination = Routes.ONE.route
    ) {
        composable(Routes.ONE.route) {
            WebView(key = it.id, modifier = Modifier.fillMaxSize())
        }
        composable(Routes.TWO.route) {
            WebView(key = it.id, modifier = Modifier.fillMaxSize())
        }
    }
}