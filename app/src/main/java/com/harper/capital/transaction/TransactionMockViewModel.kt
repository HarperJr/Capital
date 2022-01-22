package com.harper.capital.transaction

import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class TransactionMockViewModel : ComponentViewModel<TransactionState>(
    defaultState = TransactionState(selectedPage = 0)
), EventObserver<TransactionEvent> {

    override fun onEvent(event: TransactionEvent) {
        /**nope**/
    }
}
