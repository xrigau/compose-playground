package com.xrigau.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.xrigau.composeplayground.ui.theme.ComposeplaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeplaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Text(text = "Hello $name! Darken", modifier = Modifier.rainbow(type = 0))
        Text(text = "Hello $name! Lighten", modifier = Modifier.rainbow(type = 1))
        Text(text = "Hello $name! Screen", modifier = Modifier.rainbow(type = 2))
        Text(text = "Hello $name! Overlay", modifier = Modifier.rainbow(type = 3))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeplaygroundTheme {
        Greeting("Android")
    }
}

fun Modifier.rainbow(type: Int): Modifier = this.then(Rainbow(type, inspectorInfo = debugInspectorInfo {
    name = "rainbow"
}))

private class Rainbow constructor(
    private val type: Int,
    inspectorInfo: InspectorInfo.() -> Unit
) : DrawModifier, InspectorValueInfo(inspectorInfo) {

    override fun ContentDrawScope.draw() {
        drawContext.canvas.withSave {
            drawContent()
            when (type) {
                0 -> drawRect(Color.Cyan, blendMode = BlendMode.Darken)
                1 -> drawRect(Color.Cyan, blendMode = BlendMode.Lighten)
                2 -> drawRect(Color.Cyan, blendMode = BlendMode.Screen)
                3 -> drawRect(Color.Cyan, blendMode = BlendMode.Overlay)
               else -> Unit
            }
        }
    }
}