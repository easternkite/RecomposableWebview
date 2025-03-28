package com.easternkite.recomposablewebview.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes(val icon: ImageVector, val route: String) {
    ONE(icon = Icons.Default.Face, route = "one"),
    TWO(icon = Icons.Default.Settings, route = "two")
}