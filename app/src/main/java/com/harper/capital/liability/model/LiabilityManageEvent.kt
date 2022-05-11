package com.harper.capital.liability.model

import com.harper.capital.domain.model.Contact
import com.harper.capital.domain.model.Currency

sealed class LiabilityManageEvent {

    object CurrencySelectClick : LiabilityManageEvent()

    object IconSelectClick : LiabilityManageEvent()

    object BackClick : LiabilityManageEvent()

    object Apply : LiabilityManageEvent()

    object ContactSelectClick : LiabilityManageEvent()

    class CurrencySelect(val currency: Currency) : LiabilityManageEvent()

    class IconSelect(val iconName: String) : LiabilityManageEvent()

    class NameChange(val name: String) : LiabilityManageEvent()

    class AmountChange(val amount: Double) : LiabilityManageEvent()

    class TabSelect(val tabIndex: Int) : LiabilityManageEvent()

    class PhoneChange(val phone: String) : LiabilityManageEvent()

    class ContactSelect(val contact: Contact) : LiabilityManageEvent()
}
