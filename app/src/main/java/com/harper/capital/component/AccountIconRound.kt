package com.harper.capital.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme

@Composable
fun AccountIconRound(
    modifier: Modifier = Modifier,
    size: Dp = CapitalTheme.dimensions.imageMedium,
    color: AccountColor,
    icon: AccountIcon
) {
    val backgroundColor = accountBackgroundColor(color)
    CompositionLocalProvider(LocalContentColor provides accountContentColorFor(backgroundColor)) {
        Box(
            modifier = modifier
                .size(size)
                .background(color = backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(0.75f),
                imageVector = icon.getImageVector(),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountIconRoundLight() {
    CPreview {
        AccountIconRound(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            color = AccountColor.TINKOFF,
            icon = AccountIcon.TINKOFF
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountIconRoundDark() {
    CPreview(isDark = true) {
        AccountIconRound(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            color = AccountColor.TINKOFF,
            icon = AccountIcon.TINKOFF
        )
    }
}
