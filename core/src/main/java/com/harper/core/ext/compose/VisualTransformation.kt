package com.harper.core.ext.compose

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.absoluteValue

private const val MASK_SYMBOL = '?'

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        var offset = 0
        val transformedText = mask.mapIndexed { index, maskChar ->
            when (maskChar) {
                MASK_SYMBOL -> text.getOrElse(index - offset) { '#' }
                else -> {
                    offset++
                    maskChar
                }
            }
        }
        val maskedText = String(transformedText.toCharArray())
        return TransformedText(
            AnnotatedString(text = maskedText),
            offsetMapping = object : OffsetMapping {

                override fun originalToTransformed(offset: Int): Int {
                    val offsetValue = offset.absoluteValue
                    var numberOfSymbols = 0
                    val masked = mask.takeWhile {
                        if (it == MASK_SYMBOL) numberOfSymbols++
                        numberOfSymbols < offsetValue
                    }
                    return masked.length + 1
                }

                override fun transformedToOriginal(offset: Int): Int =
                    mask.take(offset.absoluteValue).count { it == MASK_SYMBOL }
            }
        )
    }
}