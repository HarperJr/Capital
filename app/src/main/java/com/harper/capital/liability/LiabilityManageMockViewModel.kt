package com.harper.capital.liability

import com.harper.capital.liability.model.LiabilityManageEvent
import com.harper.capital.liability.model.LiabilityManageState
import com.harper.core.ui.ComponentViewModel

class LiabilityManageMockViewModel : ComponentViewModel<LiabilityManageState, LiabilityManageEvent>(
    initialState = LiabilityManageState(selectedPage = 0)
) {

    override fun onEvent(event: LiabilityManageEvent) {
        /**nope**/
    }
}
