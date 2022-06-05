package com.harper.capital.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.ext.accountBackgroundColor
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

private val cardMinWidth = 92.dp

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ActionCard(
    title: String,
    backgroundColor: Brush,
    iconBackgroundColor: Color,
    icon: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.defaultMinSize(minWidth = cardMinWidth),
        shape = CapitalTheme.shapes.extraLarge,
        backgroundColor = CapitalColors.Transparent,
        elevation = 4.dp,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(brush = backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = CapitalTheme.dimensions.medium, vertical = CapitalTheme.dimensions.medium),
                verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.medium)
            ) {
                Box(
                    modifier = Modifier
                        .requiredSize(CapitalTheme.dimensions.imageMedium)
                        .background(color = iconBackgroundColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.requiredSize(20.dp)) {
                        icon.invoke(this)
                    }
                }
                Text(text = title, style = CapitalTheme.typography.buttonSmall, color = CapitalColors.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionCardLight() {
    val color = accountBackgroundColor(AccountColor.VTB)
    CapitalTheme {
        ActionCard(
            title = "Analytics",
            backgroundColor = Brush.horizontalGradient(listOf(Color(0xFF3B5DB1), Color(0xFF449BEB))),
            iconBackgroundColor = CapitalColors.White,
            icon = {
                Icon(imageVector = CapitalIcons.Cards, contentDescription = null, tint = color)
            },
            modifier = Modifier.padding(CapitalTheme.dimensions.side)
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionCardDark() {
    val color = accountBackgroundColor(AccountColor.SBER)
    CapitalTheme(isDark = true) {
        ActionCard(
            title = "Analytics",
            backgroundColor = Brush.horizontalGradient(listOf(Color(0xFF6AC783), Color(0xFF7BEA99))),
            iconBackgroundColor = CapitalColors.White,
            icon = {
                Icon(imageVector = CapitalIcons.Cards, contentDescription = null, tint = color)
            },
            modifier = Modifier.padding(CapitalTheme.dimensions.side)
        ) {}
    }
}
