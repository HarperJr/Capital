package com.harper.capital.asset

import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.domain.model.AccountColor
import com.harper.core.ui.ComponentViewModelV1
import com.harper.core.ui.EventObserver

class AssetManageMockViewModel : ComponentViewModelV1<AssetManageState, AssetManageEvent>(
    defaultState = AssetManageState(
        mode = AssetManageMode.ADD,
        isLoading = false,
        name = "Tinkoff Platinum",
        balance = 12434.0,
        colors = AccountColor.values().toList(),
        color = AccountColor.TINKOFF
    )
), EventObserver<AssetManageEvent> {

    override fun onEvent(event: AssetManageEvent) {
        /**nope**/
    }
}
