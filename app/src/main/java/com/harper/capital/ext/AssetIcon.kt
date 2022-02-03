package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.harper.capital.domain.model.AssetIcon
import com.harper.core.theme.CapitalIcons

@Composable
fun AssetIcon.getImageVector(): ImageVector = when (this) {
    AssetIcon.TINKOFF -> CapitalIcons.Bank.Tinkoff
    AssetIcon.ALPHA -> CapitalIcons.Bank.Alpha
    AssetIcon.VTB -> CapitalIcons.Bank.Vtb
    AssetIcon.SBER -> CapitalIcons.Bank.Sber
    AssetIcon.RAIFFEISEN -> CapitalIcons.Bank.Raiffeisen
    AssetIcon.BITCOIN -> CapitalIcons.Bank.Bitcoin
    AssetIcon.ETHERIUM -> CapitalIcons.Bank.Etherium
    AssetIcon.DOGECOIN -> CapitalIcons.Bank.Dogecoin
    AssetIcon.USD -> CapitalIcons.Bank.Dollar
    AssetIcon.EUR -> CapitalIcons.Bank.Euro
    AssetIcon.PIGGY_BANK -> CapitalIcons.Bank.PiggyBank
    AssetIcon.MOBILE -> CapitalIcons.Mobile
    AssetIcon.PRODUCTS -> CapitalIcons.ProductCart
    AssetIcon.WALLET -> CapitalIcons.Wallet
    AssetIcon.TRAVEL -> CapitalIcons.Travel
    AssetIcon.BUSINESS -> CapitalIcons.Business
    AssetIcon.CAR -> CapitalIcons.Car
    AssetIcon.TRANSPORT -> CapitalIcons.Transport
    AssetIcon.BANK -> CapitalIcons.BankWallet
}
