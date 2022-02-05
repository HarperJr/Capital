package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.harper.capital.domain.model.AccountIcon
import com.harper.core.theme.CapitalIcons

@Composable
fun AccountIcon.getImageVector(): ImageVector = when (this) {
    AccountIcon.TINKOFF -> CapitalIcons.Bank.Tinkoff
    AccountIcon.ALPHA -> CapitalIcons.Bank.Alpha
    AccountIcon.VTB -> CapitalIcons.Bank.Vtb
    AccountIcon.SBER -> CapitalIcons.Bank.Sber
    AccountIcon.RAIFFEISEN -> CapitalIcons.Bank.Raiffeisen
    AccountIcon.BITCOIN -> CapitalIcons.Bank.Bitcoin
    AccountIcon.ETHERIUM -> CapitalIcons.Bank.Etherium
    AccountIcon.DOGECOIN -> CapitalIcons.Bank.Dogecoin
    AccountIcon.USD -> CapitalIcons.Bank.Dollar
    AccountIcon.EUR -> CapitalIcons.Bank.Euro
    AccountIcon.PIGGY_BANK -> CapitalIcons.Bank.PiggyBank
    AccountIcon.MOBILE -> CapitalIcons.Mobile
    AccountIcon.PRODUCTS -> CapitalIcons.ProductCart
    AccountIcon.WALLET -> CapitalIcons.Wallet
    AccountIcon.TRAVEL -> CapitalIcons.Travel
    AccountIcon.BUSINESS -> CapitalIcons.Business
    AccountIcon.CAR -> CapitalIcons.Car
    AccountIcon.TRANSPORT -> CapitalIcons.Transport
    AccountIcon.BANK -> CapitalIcons.BankWallet
}
