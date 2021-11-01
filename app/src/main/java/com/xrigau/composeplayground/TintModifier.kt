package com.xrigau.composeplayground

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo

fun Modifier.tint(color: Color, blendMode: BlendMode = BlendMode.Color): Modifier =
    this.then(
        TintModifier(color, blendMode = blendMode, inspectorInfo = debugInspectorInfo {
            name = "tint"
            properties["color"] = color
            properties["blendMode"] = blendMode
        })
    )


private class TintModifier constructor(
    private val color: Color,
    private val blendMode: BlendMode,
    inspectorInfo: InspectorInfo.() -> Unit
) : DrawModifier, InspectorValueInfo(inspectorInfo) {

    override fun ContentDrawScope.draw() {
        drawContext.canvas.withSave {
            drawContent()
            drawRect(
                color,
                blendMode = blendMode,
            )
        }
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + blendMode.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? TintModifier ?: return false
        return color == otherModifier.color &&
                blendMode == otherModifier.blendMode
    }

    override fun toString(): String =
        "TintModifier(" +
                "color=$color, " +
                "blendMode=$blendMode)"
}