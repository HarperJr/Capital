package com.harper.capital.asset

import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.domain.model.AccountColor
import com.harper.core.ui.ComponentViewModel

class AssetManageMockViewModel : ComponentViewModel<AssetManageState, AssetManageEvent>(
    initialState = AssetManageState(
        mode = AssetManageMode.ADD,
        isLoading = false,
        name = "Tinkoff Platinum",
        balance = 12434.0,
        colors = AccountColor.values().toList(),
        color = AccountColor.TINKOFF
    )
) {

    override fun onEvent(event: AssetManageEvent) {
        /**nope**/
    }
}
