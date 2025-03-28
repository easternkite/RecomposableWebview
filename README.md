# RecomposableWebview
This project explains how to save and restore the WebView state during navigation or recomposition.

It provides two solutions.
1. Saving and restoring bundle of the last retrieved WebView.
2. Saving and restoring WebView instances using a custom stack.

## 1. Saving and restoring bundle of the last retrieved WebView.
This is the simplest way to save and restore the state of the WebView.
The process consists of the following steps :
1. Save the WebView state in a bundle when the Composable is disposed, using `rememberSaveable` with a unique key.
2. Restore the state from the bundle if it is not empty.

```kotlin
fun WebView(
    key: String,
    modifier: Modifier = Modifier
) {
    val savedBundle: Bundle = rememberSaveable(key) { Bundle() }

    val webView by remember(key) {
        WebView(context) { /* ... */ }
    }

    DisposableEffect(key) {
        onDispose {
            webView.saveState(savedBundle)
            webView.destroy()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { webview }
    )
}
```

### pros and cons.
* **Advantages** 
    * Simple to implement.
* **Disadvantages** 
    * The Webview instance is recreated frequantly when navigating between screens.
    * Only works on Android; not compatible with Kotlin Multiplatform (KMP)

