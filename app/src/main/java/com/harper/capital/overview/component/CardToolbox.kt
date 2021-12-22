package com.harper.capital.overview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.core.component.ComposablePreview
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun CardToolbox(
    modifier: Modifier = Modifier,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Box(modifier = modifier.background(color = CapitalTheme.colors.background)) {
        ToolbarItem(
            modifier = Modifier.align(Alignment.CenterStart),
            icon = CapitalIcons.Income,
            text = stringResource(id = R.string.income),
            onClick = onIncomeClick
        )

        ToolbarItem(
            modifier = Modifier.align(Alignment.Center),
            icon = CapitalIcons.Expense,
            text = stringResource(id = R.string.expense),
            onClick = onExpenseClick
        )

        ToolbarItem(
            modifier = Modifier.align(Alignment.CenterEnd),
            icon = CapitalIcons.Edit,
            text = stringResource(id = R.string.edit),
            onClick = onEditClick
        )
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
            colorFilter = ColorFilter.tint(color = CapitalColors.DodgerBlue)
        )
        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            text = text,
            style = CapitalTheme.typography.regular.copy(fontWeight = FontWeight.Medium),
            color = CapitalColors.DodgerBlue
        )
    }
}

@Preview
@Composable
private fun CardToolboxLight() {
    ComposablePreview {
        CardToolbox(modifier = Modifier.fillMaxWidth(), {}, {}, {})
    }
}

@Preview
@Composable
private fun CardToolboxDark() {
    ComposablePreview(isDark = true) {
        CardToolbox(modifier = Modifier.fillMaxWidth(), {}, {}, {})
    }
}

