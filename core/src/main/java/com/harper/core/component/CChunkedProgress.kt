package com.harper.core.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import com.harper.core.theme.CapitalTheme

@Composable
fun CChunkedProgress(modifier: Modifier = Modifier, chunks: List<ProgressChunk>) {
    val maxValue = remember(chunks) { chunks.sumOf { it.value } }
    Canvas(modifier = modifier) {
        drawIntoCanvas { canvas ->
            var offset = 0f
            chunks.forEach {
                val rect = calculateChunkRect(size, offset, maxValue, it.value)
                drawChunk(canvas, rect, it)
                offset += rect.width
            }
        }
    }
}

private fun calculateChunkRect(size: Size, offset: Float, maxValue: Double, value: Double): Rect = Rect(
    top = 0f,
    left = offset,
    bottom = size.height,
    right = offset + (value / maxValue).toFloat() * size.width
)

private fun drawChunk(canvas: Canvas, rect: Rect, chunk: ProgressChunk) {
    canvas.drawRect(
        rect,
        Paint().apply {
            color = chunk.color
            style = PaintingStyle.Fill
        }
    )
}

class ProgressChunk(val value: Double, val color: Color)

@Preview(showBackground = true)
@Composable
fun ChunkedProgressPreview() {
    val chunks = listOf(
        ProgressChunk(1000.0, color = Color.Green),
        ProgressChunk(781.0, color = Color.Red),
        ProgressChunk(500.0, color = Color.Blue),
        ProgressChunk(2040.0, color = Color.Gray)
    )
    CPreview {
        CChunkedProgress(
            modifier = Modifier
                .fillMaxWidth()
                .height(CapitalTheme.dimensions.large)
                .padding(CapitalTheme.dimensions.side),
            chunks = chunks
        )
    }
}