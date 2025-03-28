package com.easternkite.recomposablewebview.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * A Composable function that displays a `WebView` while preserving its state across recompositions.
 *
 * This WebView supports state persistence using `Bundle` and restores the last visited page when
 * the Composable is recreated. It also handles the back navigation within the WebView.
 *
 * ## Features:
 * - Saves and restores WebView state using `rememberSaveable`
 * - Supports back navigation using `BackHandler`
 * - Configures basic WebView settings (`JavaScript`, `DOM Storage`)
 * - Ensures WebView is properly disposed to prevent memory leaks
 *
 * @param key A unique identifier to manage WebView state separately for different instances.
 * @param modifier A `Modifier` to customize the appearance and layout of the WebView.
 */
@Composable
fun WebView(
    key: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val savedBundle: Bundle = rememberSaveable(key) { Bundle() }
    var canGoBack by remember {
        mutableStateOf(false)
    }

    val webView = remember(key) {
        WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    canGoBack = view?.canGoBack() ?: false
                    println("state started = ${view?.url}")
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    canGoBack = view?.canGoBack() ?: false
                    println("state finished = ${view?.url}")
                }
            }
            webChromeClient = WebChromeClient()
            if (savedBundle.isEmpty) {
                loadUrl("https://www.google.com")
            } else {
                restoreState(savedBundle)
                requestFocus()
                requestFocusFromTouch()
            }
        }
    }

    DisposableEffect(key) {
        onDispose {
            webView.saveState(savedBundle)
            webView.destroy()
        }
    }

    BackHandler(canGoBack) {
        webView.goBack()
    }

    AndroidView(
        modifier = modifier,
        factory = { webView }
    )
}
