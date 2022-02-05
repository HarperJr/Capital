package com.harper.capital.shelter.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.harper.capital.domain.model.Account

data class ShelterState(
    val title: String = "Title",
    val accounts: List<Account> = emptyList()
) {
    var text: String by mutableStateOf("")
    var assetStates: List<AssetState> = accounts.map { AssetState(it) }
}

class AssetState(val account: Account) {
    var amount: Double by mutableStateOf(account.balance)
}
