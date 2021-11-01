package com.xrigau.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xrigau.composeplayground.ui.theme.ComposeplaygroundTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeplaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting2("Android")
                }
            }
        }
    }
}

private val blendModes = listOf(
    BlendMode.Plus,
    BlendMode.Screen,
    BlendMode.Overlay,
    BlendMode.Lighten,
    BlendMode.ColorDodge,
    BlendMode.ColorBurn,
    BlendMode.Softlight,
    BlendMode.Color, // TODO: This is what I wanted
)

@Composable
fun Greeting2(name: String) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
//            .rainbow(blendMode = BlendMode.Color)
    ) {
        blendModes.forEach {
            key(it) {
                Row {
                    Text(
                        text = "$it", modifier = Modifier
                            .padding(4.dp)
                            .rainbow(blendMode = it)
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(4.dp)
                            .rainbow(blendMode = it)
                    ) {
                        Text(text = "Hello! $it", modifier = Modifier)
                    }
                    ImageResourceDemo(Modifier.rainbow(blendMode = it))
//                    ImageResourceDemo()
                }
            }
        }
    }
}
