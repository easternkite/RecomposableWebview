package com.easternkite.recomposablewebview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.easternkite.recomposablewebview.ui.navigation.NavBottomBar
import com.easternkite.recomposablewebview.ui.navigation.NavigationHost
import com.easternkite.recomposablewebview.ui.navigation.Routes
import com.easternkite.recomposablewebview.ui.rememberWebViewController
import com.easternkite.recomposablewebview.ui.theme.RecomposableWebViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecomposableWebViewTheme {
                var selectedRoute by rememberSaveable { mutableStateOf(Routes.ONE.route) }
                val controller = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavBottomBar(
                            selectedRoute = selectedRoute,
                            onClick = {
                                selectedRoute = it.route
                                controller.navigate(it.route) {
                                    popUpTo(controller.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    val isUsingWbController = true // you can change it to test different solution
                    if (isUsingWbController) {
                        val wbController = rememberWebViewController()
                        NavigationHost(
                            modifier = Modifier.padding(innerPadding),
                            webViewController = wbController,
                            controller = controller
                        )
                    } else {
                        NavigationHost(
                            modifier = Modifier.padding(innerPadding),
                            controller = controller
                        )
                    }
                }
            }
        }
    }
}
