package com.harper.capital.transaction

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.imePadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.transaction.component.AssetSource
import com.harper.capital.transaction.component.NewSource
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionStateProvider
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.component.CapitalButton
import com.harper.core.component.ComposablePreview
import com.harper.core.component.HorizontalSpacer
import com.harper.core.component.MenuIcon
import com.harper.core.component.TabBar
import com.harper.core.component.Toolbar
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.coroutines.flow.collect
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content(state: TransactionState, es: EventSender<TransactionEvent>) {
    Scaffold(
        backgroundColor = CapitalTheme.colors.background,
        topBar = { TransactionTopBar(es) }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                val pagerState = rememberPagerState(initialPage = state.selectedPage)
                LaunchedEffect(state) {
                    snapshotFlow { pagerState.currentPage }.collect { page ->
                        es.send(TransactionEvent.TabSelect(page))
                    }
                }
                TabBar(
                    data = state.tabBarData,
                    selectedTabIndex = pagerState.currentPage,
                    onTabSelect = { es.send(TransactionEvent.TabSelect(it)) }
                )
                HorizontalPager(
                    state = pagerState,
                    count = state.pages.size
                ) { pageIndex ->
                    val dataSets = state.pages[pageIndex].assetDataSets
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        dataSets.forEach { dataSet ->
                            HorizontalSpacer(height = 24.dp)
                            Text(text = dataSet.section.resolveTitle(), style = CapitalTheme.typography.button)
                            HorizontalSpacer(height = 8.dp)
                            FlowRow(
                                mainAxisSpacing = 8.dp,
                                crossAxisSpacing = 8.dp
                            ) {
                                dataSet.assets.forEach {
                                    AssetSource(asset = it, isSelected = it.id == dataSet.selectedAssetId) {
                                        es.send(TransactionEvent.AssetSourceSelect(dataSet.section, it))
                                    }
                                }
                                NewSource { es.send(TransactionEvent.NewSourceClick(dataSet.section)) }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            ) {
                CapitalButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    text = stringResource(id = R.string.next),
                    onClick = { }
                )
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
private fun ContentLight(
    @PreviewParameter(TransactionStateProvider::class) transactionState: TransactionState
) {
    Content(state = transactionState, es = MockEventSender())
}

@Preview(showBackground = true)
@Composable
private fun ContentDark(
    @PreviewParameter(TransactionStateProvider::class) transactionState: TransactionState
) {
    ComposablePreview(isDark = true) {
        Content(state = transactionState, es = MockEventSender())
    }
}
