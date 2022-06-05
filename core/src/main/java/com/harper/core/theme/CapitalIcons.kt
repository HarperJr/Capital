package com.harper.core.theme

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ViewCarousel
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.core.R
import com.harper.core.component.CIcon
import com.harper.core.component.CPreview
import com.harper.core.component.CWrappedGrid

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
    val AddAsset
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_add_asset)
    val History
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_history)
    val EditAsset
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_edit_asset)
    val NewAsset
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_new_asset)
    val ArrowRight
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_arrow_right)
    val ArrowLeft
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_arrow_left)
    val Calendar
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_calendar)
    val RoundCheck
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_round_check)
    val Filter
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_filter)
    val Travel
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_travel)
    val Business
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_business)
    val Car
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_car)
    val Transport
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_bus)
    val BankWallet
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_bank)
    val Repeat
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_repeat)
    val Cards
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_credit_cards)
    val Chart
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_chart)

    val Edit: ImageVector
        get() = Icons.Rounded.Edit
    val Search: ImageVector
        get() = Icons.Rounded.Search
    val Settings: ImageVector
        get() = Icons.Rounded.Settings
    val Add: ImageVector
        get() = Icons.Rounded.Add
    val Palette: ImageVector
        get() = Icons.Rounded.Palette
    val Notifications: ImageVector
        get() = Icons.Rounded.Notifications
    val Password: ImageVector
        get() = Icons.Rounded.Lock
    val Account: ImageVector
        get() = Icons.Rounded.AccountCircle
    val EyeOn: ImageVector
        get() = Icons.Rounded.Visibility
    val EyeOff: ImageVector
        get() = Icons.Rounded.VisibilityOff
    val Carousel: ImageVector
        get() = Icons.Rounded.ViewCarousel

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
        val Dogecoin: ImageVector
            @Composable
            get() = ImageVector.vectorResource(id = R.drawable.ic_doge)
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
        CapitalIcons.Edit,
        CapitalIcons.Search,
        CapitalIcons.Income,
        CapitalIcons.Expense,
        CapitalIcons.Settings,
        CapitalIcons.Add,
        CapitalIcons.List,
        CapitalIcons.AddAsset,
        CapitalIcons.History,
        CapitalIcons.EditAsset,
        CapitalIcons.NewAsset,
        CapitalIcons.ArrowLeft,
        CapitalIcons.ArrowRight,
        CapitalIcons.RoundCheck,
        CapitalIcons.Calendar,
        CapitalIcons.Filter,
        CapitalIcons.Travel,
        CapitalIcons.Business,
        CapitalIcons.Car,
        CapitalIcons.Transport,
        CapitalIcons.BankWallet,
        CapitalIcons.Repeat,
        CapitalIcons.Password,
        CapitalIcons.Account,
        CapitalIcons.EyeOn,
        CapitalIcons.EyeOff,
        CapitalIcons.Carousel,
        CapitalIcons.Cards,
        CapitalIcons.Chart
    )
    CWrappedGrid(
        modifier = Modifier.background(CapitalTheme.colors.background),
        columns = 6,
        icons
    ) {
        CIcon(imageVector = it)
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
        CapitalIcons.Bank.Dogecoin,
        CapitalIcons.Bank.PiggyBank
    )
    CWrappedGrid(
        modifier = Modifier.background(CapitalTheme.colors.background),
        columns = 6,
        icons
    ) {
        CIcon(imageVector = it)
    }
}

@Preview
@Composable
private fun IconsLight() {
    CPreview {
        Icons()
    }
}

@Preview
@Composable
private fun IconsDark() {
    CPreview(isDark = true) {
        Icons()
    }
}

@Preview
@Composable
private fun BankIconsLight() {
    CPreview {
        BankIcons()
    }
}

@Preview
@Composable
private fun BankIconsDark() {
    CPreview(isDark = true) {
        BankIcons()
    }
}


