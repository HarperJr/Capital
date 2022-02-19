package com.harper.capital.transaction

import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionState
import com.harper.core.ui.ComponentViewModel

class TransactionMockViewModel : ComponentViewModel<TransactionState, TransactionEvent>(
    initialState = TransactionState(selectedPage = 0)
) {

    override fun onEvent(event: TransactionEvent) {
        /**nope**/
    }
}
