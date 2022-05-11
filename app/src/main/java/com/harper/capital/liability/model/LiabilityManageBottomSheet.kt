package com.harper.capital.liability.model

import androidx.compose.runtime.Composable
import com.harper.capital.bottomsheet.IconsBottomSheetData
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.Contact
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.getImageVector

sealed class LiabilityManageBottomSheet {

    class Icons(private val selectedIcon: AccountIcon) : LiabilityManageBottomSheet() {
        val data: IconsBottomSheetData
            @Composable
            get() = IconsBottomSheetData(
                icons = AccountIcon.liabilityValues().map {
                    IconsBottomSheetData.Icon(it.name, it.getImageVector())
                },
                selectedIcon = selectedIcon.name
            )
    }

    class Currencies(val currencies: List<Currency>, val selectedCurrency: Currency) :
        LiabilityManageBottomSheet()

    class Contacts(val contacts: List<Contact>, val selectedContact: Contact?) :
        LiabilityManageBottomSheet()
}
