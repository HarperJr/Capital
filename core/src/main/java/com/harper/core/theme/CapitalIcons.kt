package com.harper.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
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
    val ArrowRight
        get() = Icons.Rounded.ArrowForward
    val Edit: ImageVector
        get() = Icons.Rounded.Edit
    val Search: ImageVector
        get() = Icons.Rounded.Search

    object Bank {

        val Tinkoff: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_tinkoff)
        val Alpha: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_alfa)
        val Vtb: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_vtb)
        val Sber: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_sber)
        val Raiffeisen: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_raiffeisen)
    }
}

@Composable
private fun Icons() {
    val icons = listOf(
        CapitalIcons.Wallet,
        CapitalIcons.ArrowBack,
        CapitalIcons.Edit,
        CapitalIcons.Search,
        CapitalIcons.ArrowRight
    )
    Row(modifier = Modifier.background(CapitalTheme.colors.background)) {
        icons.forEach {
            MenuIcon(imageVector = it)
        }
    }
}

@Composable
private fun BankIcons() {
    val icons = listOf(
        CapitalIcons.Bank.Tinkoff,
        CapitalIcons.Bank.Alpha,
        CapitalIcons.Bank.Vtb,
        CapitalIcons.Bank.Sber,
        CapitalIcons.Bank.Raiffeisen
    )
    Row(modifier = Modifier.background(CapitalTheme.colors.background)) {
        icons.forEach {
            MenuIcon(imageVector = it, hasTint = false)
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

@Preview
@Composable
private fun BankIconsLight() {
    ComposablePreview {
        BankIcons()
    }
}

@Preview
@Composable
private fun BankIconsDark() {
    ComposablePreview(isDark = true) {
        BankIcons()
    }
}


