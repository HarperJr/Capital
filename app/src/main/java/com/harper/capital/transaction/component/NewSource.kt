package com.harper.capital.transaction.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.core.component.ComposablePreview
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun NewSource(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(
                    color = CapitalTheme.colors.background,
                    shape = CircleShape
                )
                .border(width = 1.dp, color = CapitalTheme.colors.secondary, shape = CircleShape)
                .clickable { onClick.invoke() }
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(36.dp)
                    .background(
                        color = CapitalColors.GreyLight,
                        shape = CircleShape
                    )
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = CapitalIcons.Add,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = CapitalColors.Black)
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 24.dp),
                text = stringResource(id = R.string.add_new),
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewSourceLight() {
    ComposablePreview {
        NewSource(modifier = Modifier.padding(16.dp)) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun NewSourceDark() {
    ComposablePreview(isDark = true) {
        NewSource(modifier = Modifier.padding(16.dp)) {}
    }
}
