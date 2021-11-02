package com.xrigau.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.xrigau.composeplayground.ui.theme.ComposeplaygroundTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
        val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

        Box(
            Modifier
                .offset { offset.value.toIntOffset() }
                .background(Color.Gray)
                .height(50.dp)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    coroutineScope {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consumeAllChanges()

                                launch {
                                    offset.snapTo(offset.value + Offset(0f, dragAmount.y))
                                }
                            },
                            onDragEnd = {
                                launch {
                                    offset.animateTo(Offset.Zero)
                                }
                            }
                        )
                    }
                }
        )
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())
