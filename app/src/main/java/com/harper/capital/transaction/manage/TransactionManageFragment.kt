package com.harper.capital.transaction.manage

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.R
import com.harper.capital.domain.model.TransactionType
import com.harper.capital.transaction.manage.component.TransactionHeader
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CButton
import com.harper.core.component.CDatePicker
import com.harper.core.component.CDatePickerDialog
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CLoaderLayout
import com.harper.core.component.CPreferenceSwitch
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CTextField
import com.harper.core.component.CToolbar
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

class TransactionManageFragment : ComponentFragment<TransactionManageViewModel>(),
    EventSender<TransactionManageEvent> {
    override val viewModel: TransactionManageViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            TransactionManageScreen(viewModel, this)
        }
    }

    @Parcelize
    class Params(val transactionType: TransactionType, val assetFromId: Long, val assetToId: Long) :
        Parcelable

    companion object {
        private const val PARAMS = "transaction_manage_params"

        fun newInstance(params: Params): TransactionManageFragment =
            TransactionManageFragment().withArgs(PARAMS to params)
    }
}

@Composable
private fun TransactionManageScreen(
    viewModel: ComponentViewModel<TransactionManageState>,
    es: EventSender<TransactionManageEvent>
) {
    val state by viewModel.state.collectAsState()
    CScaffold(topBar = { TransactionManageTopBar(state, es) }) {
        CLoaderLayout(isLoading = state.isLoading, loaderContent = {}) {
            if (state.datePickerDialogState.isVisible) {
                CDatePickerDialog(
                    date = state.datePickerDialogState.date,
                    onDismiss = {
                        es.send(TransactionManageEvent.HideDialog)
                    },
                    onDateSelect = { es.send(TransactionManageEvent.DateSelect(it)) }
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = CapitalTheme.dimensions.side)
                ) {
                    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                    val assetPair = state.assetPair
                    if (assetPair != null) {
                        TransactionHeader(source = assetPair.first, receiver = assetPair.second)
                    }
                    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                    CAmountTextField(
                        modifier = Modifier.fillMaxWidth(),
                        amount = state.amount,
                        placeholder = stringResource(id = R.string.enter_amount_hint),
                        currencyIso = state.currency.name,
                        title = {
                            Text(text = stringResource(id = R.string.amount))
                        },
                        onValueChange = { es.send(TransactionManageEvent.AmountChange(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    Text(text = stringResource(id = R.string.date))
                    CDatePicker(
                        dateStart = LocalDate.now().minusDays(30),
                        dateEnd = LocalDate.now(),
                        date = state.date,
                        onDateSelectClick = { es.send(TransactionManageEvent.DateSelectClick(it)) },
                        onDateSelect = { es.send(TransactionManageEvent.DateSelect(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    CTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.comment.orEmpty(),
                        placeholder = stringResource(id = R.string.enter_comment_hint),
                        title = { OptionalCommentTitle() },
                        onValueChange = { es.send(TransactionManageEvent.CommentChange(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    CPreferenceSwitch(
                        title = stringResource(id = R.string.schedule_transaction),
                        isChecked = state.isScheduled,
                        onCheckedChange = { es.send(TransactionManageEvent.ScheduledCheckChange(it)) }
                    )
                }
                CButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(CapitalTheme.dimensions.side),
                    text = stringResource(id = R.string.create_new_transaction),
                    onClick = { es.send(TransactionManageEvent.Apply) }
                )
            }
        }
    }
}

@Composable
private fun OptionalCommentTitle() {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = stringResource(id = R.string.comment),
            color = CapitalTheme.colors.textPrimary
        )
        Text(
            modifier = Modifier.padding(start = CapitalTheme.dimensions.small),
            text = stringResource(id = R.string.optional).lowercase(),
            style = CapitalTheme.typography.regularSmall,
            color = CapitalTheme.colors.textSecondary
        )
    }
}

@Composable
private fun TransactionManageTopBar(
    state: TransactionManageState,
    es: EventSender<TransactionManageEvent>
) {
    CToolbar(
        content = {
            Text(
                text = state.transactionType.resolveTitle(),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            CIcon(imageVector = CapitalIcons.ArrowLeft, onClick = {
                es.send(TransactionManageEvent.BackClick)
            })
        }
    )
}

@Composable
private fun TransactionType.resolveTitle() = when (this) {
    TransactionType.EXPENSE -> stringResource(id = R.string.expense)
    TransactionType.INCOME -> stringResource(id = R.string.income)
    TransactionType.SEND -> stringResource(id = R.string.send)
    TransactionType.DUTY -> stringResource(id = R.string.duty)
}

@Preview(showBackground = true)
@Composable
private fun TransactionManageScreenLight() {
    CPreview {
        TransactionManageScreen(
            viewModel = TransactionManageMockViewModel(),
            es = MockEventSender()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionManageScreenDark() {
    CPreview(isDark = true) {
        TransactionManageScreen(
            viewModel = TransactionManageMockViewModel(),
            es = MockEventSender()
        )
    }
}

