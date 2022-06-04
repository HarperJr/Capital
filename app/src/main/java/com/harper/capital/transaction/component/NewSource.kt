package com.harper.capital.transaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

private val requiredWidth = 86.dp

@Composable
fun NewSource(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .requiredWidth(requiredWidth)
            .clip(shape = CapitalTheme.shapes.extraLarge)
            .clickable { onClick.invoke() }
            .padding(
                horizontal = CapitalTheme.dimensions.small,
                vertical = CapitalTheme.dimensions.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.small)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = CapitalTheme.colors.primaryVariant, shape = CapitalTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = CapitalIcons.Add,
                contentDescription = null,
                tint = CapitalTheme.colors.onPrimary
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "",
                style = CapitalTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = "",
                style = CapitalTheme.typography.regular.copy(fontSize = 10.sp),
                color = CapitalTheme.colors.textSecondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewSourceLight() {
    CPreview {
        NewSource(modifier = Modifier.padding(16.dp)) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun NewSourceDark() {
    CPreview(isDark = true) {
        NewSource(modifier = Modifier.padding(16.dp)) {}
    }
}
