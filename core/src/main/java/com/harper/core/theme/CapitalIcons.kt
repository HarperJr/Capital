package com.harper.core.theme

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.core.R
import com.harper.core.component.ComposablePreview
import com.harper.core.component.Grid
import com.harper.core.component.MenuIcon

object CapitalIcons {

    val Wallet: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_account_balance_wallet)
    val ProductCart: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_product_cart)
    val Mobile: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_mobile)
    val Income: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_income)
    val Expense: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_expense)
    val List: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_list)
    val Navigation
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_navigation)

    val ArrowRight
        get() = Icons.Rounded.ArrowForward
    val Edit: ImageVector
        get() = Icons.Rounded.Edit
    val Search: ImageVector
        get() = Icons.Rounded.Search
    val Settings: ImageVector
        get() = Icons.Rounded.Settings

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
        val Euro: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_eur)
        val Dollar: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_usd)
        val Etherium: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_eth)
        val Bitcoin: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_btc)
        val PiggyBank: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_piggy_bank)
    }
}

@Composable
private fun Icons() {
    val icons = listOf(
        CapitalIcons.Wallet,
        CapitalIcons.ProductCart,
        CapitalIcons.Mobile,
        CapitalIcons.Navigation,
        CapitalIcons.Edit,
        CapitalIcons.Search,
        CapitalIcons.ArrowRight,
        CapitalIcons.Income,
        CapitalIcons.Expense,
        CapitalIcons.Settings,
        CapitalIcons.List
    )
    Grid(modifier = Modifier.background(CapitalTheme.colors.background), columns = 6, icons) {
        MenuIcon(imageVector = it)
    }
}

@Composable
private fun BankIcons() {
    val icons = listOf(
        CapitalIcons.Bank.Tinkoff,
        CapitalIcons.Bank.Alpha,
        CapitalIcons.Bank.Vtb,
        CapitalIcons.Bank.Sber,
        CapitalIcons.Bank.Raiffeisen,
        CapitalIcons.Bank.Euro,
        CapitalIcons.Bank.Dollar,
        CapitalIcons.Bank.Etherium,
        CapitalIcons.Bank.Bitcoin,
        CapitalIcons.Bank.PiggyBank
    )
    Grid(modifier = Modifier.background(CapitalTheme.colors.background), columns = 6, icons) {
        MenuIcon(imageVector = it)
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


