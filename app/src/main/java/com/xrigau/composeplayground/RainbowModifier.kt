package com.xrigau.composeplayground

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo

fun Modifier.rainbow(blendMode: BlendMode = BlendMode.Color): Modifier =
    this.then(
        RainbowModifier(blendMode = blendMode, inspectorInfo = debugInspectorInfo {
            name = "rainbow"
            properties["blendMode"] = blendMode
        })
    )

private class RainbowModifier constructor(
    private val blendMode: BlendMode,
    inspectorInfo: InspectorInfo.() -> Unit
) : DrawModifier, InspectorValueInfo(inspectorInfo) {

    private val rainbowBrush = Brush.horizontalGradient(
        listOf(
            Color.Red,
            Color.Yellow,
            Color.Green,
            Color.Cyan,
            Color.Blue,
            Color.Magenta
        ),
        tileMode = TileMode.Repeated
    )

    override fun ContentDrawScope.draw() {
        drawContext.canvas.withSave {
            drawContent()
            drawRect(
                rainbowBrush,
                blendMode = blendMode,
            )
        }
    }

    override fun hashCode(): Int {
        return blendMode.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? RainbowModifier ?: return false
        return blendMode == otherModifier.blendMode
    }

    override fun toString(): String =
        "RainbowModifier(" +
                "blendMode=$blendMode)"
}