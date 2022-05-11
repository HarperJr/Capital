package com.harper.capital.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.Currency
import com.harper.capital.main.domain.model.Summary
import com.harper.core.component.CChunkedProgress
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.ProgressChunk
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val MMMMDateTimeFormatter = DateTimeFormatter.ofPattern("LLLL")

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun OperationsActionCard(modifier: Modifier = Modifier, summary: Summary, chunks: List<ProgressChunk>, onClick: () -> Unit) {
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
        Column(modifier = Modifier.padding(CapitalTheme.dimensions.medium)) {
            val balance = remember(summary) { summary.balance - summary.expenses }
            val period = stringResource(id = R.string.in_period, LocalDate.now().format(MMMMDateTimeFormatter))
            Text(text = "$period ${balance.formatWithCurrencySymbol(summary.currency.name)}", style = CapitalTheme.typography.regularSmall)
            CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
            CChunkedProgress(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CapitalTheme.dimensions.medium + 2.dp)
                    .clip(CircleShape),
                chunks = chunks
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OperationsActionCardLight() {
    CapitalTheme {
        OperationsActionCard(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            summary = Summary(1000.0, 3500.0, Currency.RUB),
            chunks = listOf(
                ProgressChunk(700.0, Color.Blue),
                ProgressChunk(415.0, Color.Red),
                ProgressChunk(776.0, Color.Green),
                ProgressChunk(2000.0, Color.Blue),
                ProgressChunk(4000.0, Color.Gray)
            )
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun OperationsActionCardDark() {
    CapitalTheme(isDark = true) {
        OperationsActionCard(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            summary = Summary(1000.0, 3500.0, Currency.RUB),
            chunks = listOf(
                ProgressChunk(1000.0, Color.Blue),
                ProgressChunk(500.0, Color.Red),
                ProgressChunk(412.0, Color.Green),
                ProgressChunk(524.0, Color.Blue),
                ProgressChunk(3000.0, Color.Gray)
            )
        ) {}
    }
}
