package com.harper.capital.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetMenu(
    color: AccountColor,
    modifier: Modifier = Modifier,
    onHistoryClick: () -> Unit,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit,
    onEditClick: (() -> Unit)? = null
) {
    val cardBackgroundColor = accountBackgroundColor(color)
    Card(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = cardBackgroundColor,
        contentColor = accountContentColorFor(cardBackgroundColor),
        shape = CapitalTheme.shapes.extraLarge
    ) {
        CompositionLocalProvider(LocalIndication provides rememberRipple(bounded = false)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = CapitalTheme.dimensions.medium, horizontal = CapitalTheme.dimensions.large),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ToolbarItem(
                    icon = CapitalIcons.History,
                    text = stringResource(id = R.string.history),
                    onClick = onHistoryClick
                )

                ToolbarItem(
                    icon = CapitalIcons.Income,
                    text = stringResource(id = R.string.income),
                    onClick = onIncomeClick
                )

                ToolbarItem(
                    icon = CapitalIcons.Expense,
                    text = stringResource(id = R.string.expense),
                    onClick = onExpenseClick
                )

                onEditClick?.let {
                    ToolbarItem(
                        icon = CapitalIcons.EditAsset,
                        text = stringResource(id = R.string.edit),
                        onClick = it
                    )
                }
            }
        }
    }
}

@Composable
private fun ToolbarItem(modifier: Modifier = Modifier, icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(modifier = modifier.clickable { onClick.invoke() }) {
        Image(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = LocalContentColor.current)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = CapitalTheme.typography.regularSmall,
            text = text,
        )
    }
}

@Preview
@Composable
private fun AssetMenuLight() {
    CPreview {
        AssetMenu(
            color = AccountColor.RAIFFEIZEN,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), {}, {}, {}, {}
        )
    }
}

@Preview
@Composable
private fun AssetMenuDark() {
    CPreview(isDark = true) {
        AssetMenu(
            color = AccountColor.SBER,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), {}, {}, {}, {}
        )
    }
}

