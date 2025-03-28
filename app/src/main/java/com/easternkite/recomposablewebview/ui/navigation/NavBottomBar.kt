package com.easternkite.recomposablewebview.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavBottomBar(
    selectedRoute: String,
    onClick: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        Routes.entries.forEach {
            NavigationBarItem(
                icon = { Icon(it.icon, contentDescription = it.name) },
                label = { Text(it.name) },
                selected = selectedRoute == it.route,
                onClick = { onClick(it) }
            )
        }
    }
}
