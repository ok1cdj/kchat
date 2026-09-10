package org.ok1cdj.kchat.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint

/**
 * Desaturate everything drawn by the composable to grayscale. Used on color-emoji
 * avatars so they render monochrome on the 1-bit e-ink panel instead of in color.
 * The color emoji font ignores colorScheme, so we filter the rendered pixels via a
 * saturation-0 ColorMatrix applied to an offscreen layer.
 */
fun Modifier.grayscale(): Modifier = this.drawWithContent {
    val paint = Paint().apply {
        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
    }
    drawIntoCanvas { canvas ->
        canvas.saveLayer(Rect(Offset.Zero, size), paint)
        drawContent()
        canvas.restore()
    }
}
