package com.xrigau.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xrigau.composeplayground.ui.theme.ComposeplaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeplaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background, modifier = Modifier.rainbow()) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Row {
        Text(
            text = "Hello $name!", modifier = Modifier
                .padding(4.dp)
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(4.dp)
        ) {
            Text(text = "Hello! $name!", modifier = Modifier)
        }
        ImageResourceDemo()
    }
}

@Composable
fun ImageResourceDemo(modifier: Modifier = Modifier) {
    val image: Painter = painterResource(id = R.drawable.composelogo)
    Image(painter = image, contentDescription = "", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeplaygroundTheme {
        Greeting("Android")
    }
}