package com.harper.capital.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionCard(modifier: Modifier = Modifier, title: String, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        shape = CapitalTheme.shapes.extraLarge,
        backgroundColor = CapitalTheme.colors.background,
        elevation = 0.dp,
        border = BorderStroke(
            width = 2.dp,
            color = CapitalTheme.colors.primaryVariant
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(CapitalTheme.dimensions.medium),
            verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.medium)
        ) {
            Text(text = title, style = CapitalTheme.typography.subtitle)
            Box(
                modifier = Modifier
                    .size(CapitalTheme.dimensions.imageMedium)
                    .background(
                        color = CapitalTheme.colors.onBackground,
                        shape = CapitalTheme.shapes.large
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionCardLight() {
    CapitalTheme {
        ActionCard(modifier = Modifier.padding(CapitalTheme.dimensions.side), title = "Analytics") {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionCardDark() {
    CapitalTheme(isDark = true) {
        ActionCard(modifier = Modifier.padding(CapitalTheme.dimensions.side), title = "Analytics") {}
    }
}
