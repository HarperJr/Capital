package com.harper.capital.transaction

import android.os.Parcelable
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.R
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon
import com.harper.core.component.TabBar
import com.harper.core.component.Toolbar
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

class TransactionFragment : ComponentFragment<TransactionViewModel>(), EventSender<TransactionEvent> {
    override val viewModel: TransactionViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        Content(state, this)
    }

    @Parcelize
    class Params(val assetId: Long?, val transactionType: TransactionType) : Parcelable

    companion object {
        private const val PARAMS = "transaction_params"

        fun newInstance(params: Params): TransactionFragment =
            TransactionFragment().withArgs(PARAMS to params)
    }
}

@Composable
private fun Content(state: TransactionState, es: EventSender<TransactionEvent>) {
    Scaffold(
        backgroundColor = CapitalTheme.colors.background,
        topBar = { TransactionTopBar(es) }
    ) {
        TabBar(data = state.tabBarData, onTabSelect = { es.send(TransactionEvent.OnTabSelect(it)) })
    }
}

@Composable
private fun TransactionTopBar(es: EventSender<TransactionEvent>) {
    Toolbar(
        title = {
            Text(
                text = stringResource(id = R.string.new_transaction_title),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            MenuIcon(imageVector = CapitalIcons.ArrowLeft, onClick = {
                es.send(TransactionEvent.BackClick)
            })
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentLight() {
    Content(state = TransactionState(transactionType = TransactionType.INCOME), es = MockEventSender())
}

@Preview(showBackground = true)
@Composable
private fun ContentDark() {
    ComposablePreview(isDark = true) {
        Content(state = TransactionState(transactionType = TransactionType.EXPENSE), es = MockEventSender())
    }
}
