package com.xrigau.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.xrigau.composeplayground.ui.theme.ComposeplaygroundTheme
import kotlin.math.roundToInt

class GesturesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeplaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background, modifier = Modifier.rainbow()) {
                    DraggableContent()
                }
            }
        }
    }
}

@Composable
fun DraggableContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        val offsetYAnim: Float by animateFloatAsState(offsetY)

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetYAnim.roundToInt()) }
                .background(Color.Gray)
                .height(50.dp)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures(onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
//                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    },
                        onDragEnd = { offsetY = 0f })
                }
        )
    }
}