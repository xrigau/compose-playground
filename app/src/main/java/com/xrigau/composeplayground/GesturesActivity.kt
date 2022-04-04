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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.xrigau.composeplayground.ui.theme.ComposeplaygroundTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class AttachPoint { Top, Bottom }

data class FeedItemView(
    val title: @Composable () -> Unit,
    val content: @Composable () -> Unit,
//    val isExpanded: Boolean,
)

data class FeedItemViewState(
    val item: FeedItemView,
    var offset: Offset,
    var rotationX: Float,
    var attachPoint: AttachPoint,
)

data class Feed(
    val items: List<FeedItemViewState>,
)


@Composable
fun DraggableContent(item: FeedItemViewState, onUpdateItem: (FeedItemViewState) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
        LaunchedEffect(offset.value) {
            val newItem = item.copy(
                offset = offset.value,
                rotationX = 360.0f * offset.value.y / 100f,

                )
            onUpdateItem(newItem)
        }

        Column(modifier = Modifier.offset { offset.value.toIntOffset() }) {
            Box(
                Modifier
                    .background(Color.Gray)
                    .rainbow()
                    .height(50.dp)
                    .fillMaxWidth()
                    .graphicsLayer(
                        rotationX = item.rotationX,
                        transformOrigin = TransformOrigin(0.5f, if (item.attachPoint == AttachPoint.Top) 0.0f else 1.0f)
                    )
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "YOLO!")
            }
        }
    }
}


class GesturesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeplaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        val item = FeedItemViewState(FeedItemView({}, {}), Offset.Zero, 0.0f, AttachPoint.Top)
                        DraggableContent(item) {
                            item.attachPoint = it.attachPoint
                            item.offset = it.offset
                            item.rotationX = it.rotationX
                        }

                        val item2 = FeedItemViewState(FeedItemView({}, {}), Offset.Zero, 0.0f, AttachPoint.Bottom)
                        DraggableContent(item2) {
                            item2.attachPoint = it.attachPoint
                            item2.offset = it.offset
                            item2.rotationX = it.rotationX
                        }
//                        DraggableContent {
//                            Box(
//                                contentAlignment = Alignment.Center,
//                                modifier = Modifier
//                                    .background(Color.Gray)
//                                    .fillMaxWidth()
//                                    .height(200.dp)
//                            ) {
//                                Text(text = "YOLO!")
//                            }
//                        }
//
//                        DraggableContent {
//                            Box(
//                                contentAlignment = Alignment.Center,
//                                modifier = Modifier
//                                    .background(Color.Gray)
//                                    .fillMaxWidth()
//                                    .height(200.dp)
//                            ) {
//                                Text(text = "YOLO!")
//                            }
//                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DraggableContent(content: @Composable () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

        Column(modifier = Modifier.offset { offset.value.toIntOffset() }) {
            Box(
                Modifier
                    .background(Color.Gray)
                    .rainbow()
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
            content()
        }
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())
