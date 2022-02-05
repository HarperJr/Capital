package com.harper.capital.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier,
    color: AccountColor,
    onHistoryClick: () -> Unit,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit,
    onEditClick: (() -> Unit)? = null
) {
    val cardBackgroundColor = accountBackgroundColor(color)
    Card(
        modifier = modifier,
        elevation = 6.dp,
        backgroundColor = cardBackgroundColor,
        contentColor = accountContentColorFor(cardBackgroundColor),
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            ToolbarItem(
                modifier = Modifier.weight(1f),
                icon = CapitalIcons.History,
                text = stringResource(id = R.string.history),
                onClick = onHistoryClick
            )

            ToolbarItem(
                modifier = Modifier.weight(1f),
                icon = CapitalIcons.Income,
                text = stringResource(id = R.string.income),
                onClick = onIncomeClick
            )

            ToolbarItem(
                modifier = Modifier.weight(1f),
                icon = CapitalIcons.Expense,
                text = stringResource(id = R.string.expense),
                onClick = onExpenseClick
            )

            onEditClick?.let {
                ToolbarItem(
                    modifier = Modifier.weight(1f),
                    icon = CapitalIcons.EditAsset,
                    text = stringResource(id = R.string.edit),
                    onClick = it
                )
            }
        }
    }
}

@Composable
private fun ToolbarItem(modifier: Modifier = Modifier, icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(modifier = modifier.clickable { onClick.invoke() }) {
        Image(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = LocalContentColor.current)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = CapitalTheme.typography.titleSmall,
            text = text,
        )
    }
}

@Preview
@Composable
private fun CardToolbarLight() {
    CPreview {
        Box(modifier = Modifier.background(CapitalTheme.colors.background)) {
            AssetMenu(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), color = AccountColor.RAIFFEIZEN, {}, {}, {}, {})
        }
    }
}

@Preview
@Composable
private fun CardbarboxDark() {
    CPreview(isDark = true) {
        Box(modifier = Modifier.background(CapitalTheme.colors.background)) {
            AssetMenu(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), color = AccountColor.SBER, {}, {}, {}, {})
        }
    }
}

