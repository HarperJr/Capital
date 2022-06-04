package com.harper.capital.main.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.ext.accountBackgroundColor
import com.harper.core.theme.CapitalTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ActionCard(modifier: Modifier = Modifier, color: Color, title: String, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        shape = CapitalTheme.shapes.extraLarge,
        backgroundColor = color,
        elevation = 0.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.medium, vertical = CapitalTheme.dimensions.side),
            verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.medium)
        ) {
            Text(text = title, style = CapitalTheme.typography.titleSmall)
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
        ActionCard(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            color = accountBackgroundColor(AccountColor.SBER),
            title = "Analytics"
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionCardDark() {
    CapitalTheme(isDark = true) {
        ActionCard(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            color = accountBackgroundColor(AccountColor.VTB_OLD),
            title = "Analytics"
        ) {}
    }
}
