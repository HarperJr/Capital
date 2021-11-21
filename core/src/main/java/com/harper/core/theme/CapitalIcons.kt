package com.harper.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.core.R
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon

object CapitalIcons {

    val Wallet: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_account_balance_wallet)

    val ArrowBack
        get() = Icons.Rounded.ArrowBack
    val Edit: ImageVector
        get() = Icons.Rounded.Edit
}

@Composable
private fun Icons() {
    val icons = listOf(CapitalIcons.Wallet, CapitalIcons.ArrowBack, CapitalIcons.Edit)
    Row(modifier = Modifier.background(CapitalTheme.colors.background)) {
        icons.forEach {
            MenuIcon(imageVector = it)
        }
    }
}

@Preview
@Composable
private fun IconsLight() {
    ComposablePreview {
        Icons()
    }
}

@Preview
@Composable
private fun IconsDark() {
    ComposablePreview(isDark = true) {
        Icons()
    }
}


