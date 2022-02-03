package com.harper.capital.asset

import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.domain.model.AssetColor
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.ComponentViewModelV1
import com.harper.core.ui.EventObserver

class AssetManageMockViewModel : ComponentViewModelV1<AssetManageState, AssetManageEvent>(
    defaultState = AssetManageState(
        mode = AssetManageMode.ADD,
        name = "Tinkoff Platinum",
        amount = 12434.0,
        colors = AssetColor.values().toList(),
        color = AssetColor.TINKOFF
    )
), EventObserver<AssetManageEvent> {

    override fun onEvent(event: AssetManageEvent) {
        /**nope**/
    }
}
