package com.harper.capital.main.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import kotlin.math.roundToInt

private val cardMinWidth = 92.dp
private const val BACKGROUND_IMAGE_OFFSET_FACTOR = 0.1f
private const val BACKGROUND_IMAGE_SCALE_FACTOR = 1.25f
private const val ACTION_CARD_RATIO = 1.4f

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ActionCard(
    title: String,
    @DrawableRes backgroundImageRes: Int,
    backgroundColor: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .defaultMinSize(minWidth = cardMinWidth),
        shape = CapitalTheme.shapes.extraLarge,
        backgroundColor = CapitalColors.Transparent,
        elevation = 4.dp,
        onClick = onClick
    ) {
        val image: @Composable () -> Unit = {
            Image(
                modifier = Modifier.scale(BACKGROUND_IMAGE_SCALE_FACTOR),
                painter = painterResource(id = backgroundImageRes), contentDescription = null,
                alpha = 0.9f
            )
        }
        val content: @Composable () -> Unit = {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(CapitalTheme.dimensions.medium),
                    text = title,
                    style = CapitalTheme.typography.buttonSmall,
                    color = CapitalColors.White
                )
            }
        }
        SubcomposeLayout(
            modifier = Modifier.background(brush = backgroundColor),
            measurePolicy = { constraints ->
                val imagePlaceables = subcompose(ActionCardContent.BACKGROUND_IMAGE, image).map { it.measure(constraints) }
                val height = if (constraints.hasBoundedHeight) {
                    imagePlaceables.maxOf { it.height }.coerceIn(constraints.minHeight, constraints.maxHeight)
                } else {
                    constraints.maxHeight
                }
                val width = if (constraints.hasBoundedHeight) {
                    (height * ACTION_CARD_RATIO).roundToInt().coerceIn(constraints.minWidth, constraints.maxWidth)
                } else {
                    constraints.maxWidth
                }
                val contentPlaceables = subcompose(ActionCardContent.CONTENT, content).map {
                    it.measure(
                        constraints.copy(
                            maxWidth = width,
                            maxHeight = height
                        )
                    )
                }
                val yOffset = height * BACKGROUND_IMAGE_OFFSET_FACTOR
                val xOffset = width * BACKGROUND_IMAGE_OFFSET_FACTOR
                layout(width, height) {
                    imagePlaceables.forEach {
                        it.place((width - height) - xOffset.roundToInt() / 4, yOffset.roundToInt())
                    }
                    contentPlaceables.forEach { it.place(0, 0) }
                }
            }
        )
    }
}

private enum class ActionCardContent {
    BACKGROUND_IMAGE,
    CONTENT
}

@Preview(showBackground = true)
@Composable
private fun ActionCardLight() {
    CapitalTheme {
        ActionCard(
            title = "Accounts",
            backgroundColor = Brush.horizontalGradient(listOf(Color(0xFF283180), Color(0xFF4974DA))),
            backgroundImageRes = R.drawable.wallet,
            modifier = Modifier.padding(CapitalTheme.dimensions.side)
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionCardDark() {
    CapitalTheme(isDark = true) {
        ActionCard(
            title = "Accounts",
            backgroundColor = Brush.horizontalGradient(listOf(Color(0xFF283180), Color(0xFF4974DA))),
            backgroundImageRes = R.drawable.wallet,
            modifier = Modifier.padding(CapitalTheme.dimensions.side)
        ) {}
    }
}
