package com.harper.capital.transaction

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.transaction.component.AssetSource
import com.harper.capital.transaction.component.NewSource
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CButton
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.TabBar
import com.harper.core.component.Toolbar
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

class TransactionFragment : ComponentFragment<TransactionViewModel>(),
    EventSender<TransactionEvent> {
    override val viewModel: TransactionViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            TransactionScreen(viewModel, this)
        }
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
@OptIn(ExperimentalPagerApi::class)
private fun TransactionScreen(
    viewModel: ComponentViewModel<TransactionState>,
    es: EventSender<TransactionEvent>
) {
    val state by viewModel.state.collectAsState()

    CScaffold(
        topBar = { TransactionTopBar(es) },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                val pagerState = rememberPagerState(initialPage = state.selectedPage)
                TabBar(
                    data = state.tabBarData,
                    pagerState = pagerState,
                    onTabSelect = { es.send(TransactionEvent.TabSelect(it)) }
                )
                HorizontalPager(
                    state = pagerState,
                    count = state.pages.size
                ) { pageIndex ->
                    PageBlock(page = state.pages[pageIndex], es = es)
                }
            }
            CButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.next),
                enabled = state.isApplyButtonEnabled,
                onClick = { es.send(TransactionEvent.Apply) }
            )
        }
    }
}

@Composable
private fun PageBlock(page: TransactionPage, es: EventSender<TransactionEvent>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        page.assetDataSets.forEach { dataSet ->
            CHorizontalSpacer(height = 24.dp)
            Text(text = dataSet.section.resolveTitle(), style = CapitalTheme.typography.button)
            CHorizontalSpacer(height = 8.dp)
            FlowRow(
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp
            ) {
                dataSet.assets.forEach {
                    AssetSource(asset = it, isSelected = it.id == dataSet.selectedAssetId) {
                        es.send(TransactionEvent.AssetSourceSelect(page.type, dataSet.section, it))
                    }
                }
                NewSource { es.send(TransactionEvent.NewSourceClick(page.type, dataSet.type)) }
            }
        }
    }
}

@Composable
private fun DataSetSection.resolveTitle(): String = when (this) {
    DataSetSection.FROM -> stringResource(id = R.string.from)
    DataSetSection.TO -> stringResource(id = R.string.to)
}

@Composable
private fun TransactionTopBar(es: EventSender<TransactionEvent>) {
    Toolbar(
        content = {
            Text(
                text = stringResource(id = R.string.new_transaction_title),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            CIcon(imageVector = CapitalIcons.ArrowLeft, onClick = {
                es.send(TransactionEvent.BackClick)
            })
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentLight() {
    TransactionScreen(TransactionMockViewModel(), es = MockEventSender())
}

@Preview(showBackground = true)
@Composable
private fun ContentDark() {
    CPreview(isDark = true) {
        TransactionScreen(TransactionMockViewModel(), es = MockEventSender())
    }
}
