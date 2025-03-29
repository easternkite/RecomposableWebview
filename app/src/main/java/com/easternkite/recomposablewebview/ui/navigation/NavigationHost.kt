package com.easternkite.recomposablewebview.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easternkite.recomposablewebview.ui.WebView
import com.easternkite.recomposablewebview.ui.WebViewController

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

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    webViewController: WebViewController,
    controller: NavHostController = rememberNavController()
) {
    fun isPop(id: String, isBottomItem: Boolean) = if (isBottomItem) {
        false
    } else {
        controller
            .currentBackStack.value
            .none { it.id == id }
    }

    NavHost(
        modifier = modifier,
        navController = controller,
        startDestination = Routes.ONE.route
    ) {
        composable(Routes.ONE.route) {
            WebView(
                key = it.id,
                controller = webViewController,
                isPop = { isPop(Routes.ONE.route, true) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(Routes.TWO.route) {
            WebView(
                key = it.id,
                controller = webViewController,
                isPop = { isPop(Routes.TWO.route, true).also { println("isPop = $it") } },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
