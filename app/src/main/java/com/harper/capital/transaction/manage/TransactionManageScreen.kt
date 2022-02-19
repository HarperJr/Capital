package com.harper.capital.transaction.manage

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
import com.google.accompanist.insets.imePadding
import com.harper.capital.R
import com.harper.capital.transaction.manage.component.TransactionHeader
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.capital.transaction.model.TransactionType
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
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDate

@Composable
fun TransactionManageScreen(
    viewModel: ComponentViewModel<TransactionManageState, TransactionManageEvent>
) {
    val state by viewModel.state.collectAsState()
    CScaffold(topBar = { TransactionManageTopBar(viewModel) }) {
        CLoaderLayout(isLoading = state.isLoading, loaderContent = {}) {
            if (state.datePickerDialogState.isVisible) {
                CDatePickerDialog(
                    date = state.datePickerDialogState.date,
                    onDismiss = {
                        viewModel.onEvent(TransactionManageEvent.HideDialog)
                    },
                    onDateSelect = { viewModel.onEvent(TransactionManageEvent.DateSelect(it)) }
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = CapitalTheme.dimensions.side)
                ) {
                    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                    val assetPair = state.accountPair
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
                        onValueChange = { viewModel.onEvent(TransactionManageEvent.AmountChange(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    Text(text = stringResource(id = R.string.date))
                    CDatePicker(
                        dateStart = LocalDate.now().minusDays(30),
                        dateEnd = LocalDate.now(),
                        date = state.date,
                        onDateSelectClick = { viewModel.onEvent(TransactionManageEvent.DateSelectClick(it)) },
                        onDateSelect = { viewModel.onEvent(TransactionManageEvent.DateSelect(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    CTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.comment.orEmpty(),
                        placeholder = stringResource(id = R.string.enter_comment_hint),
                        title = { OptionalCommentTitle() },
                        onValueChange = { viewModel.onEvent(TransactionManageEvent.CommentChange(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    CPreferenceSwitch(
                        title = stringResource(id = R.string.schedule_transaction),
                        isChecked = state.isScheduled,
                        onCheckedChange = { viewModel.onEvent(TransactionManageEvent.ScheduledCheckChange(it)) }
                    )
                }
                val applyButtonText = when (state.mode) {
                    TransactionManageMode.ADD -> stringResource(id = R.string.create_new_transaction)
                    TransactionManageMode.EDIT -> stringResource(id = R.string.save)
                }
                CButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(CapitalTheme.dimensions.side)
                        .imePadding(),
                    text = applyButtonText,
                    onClick = { viewModel.onEvent(TransactionManageEvent.Apply) }
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
private fun TransactionManageTopBar(viewModel: ComponentViewModel<TransactionManageState, TransactionManageEvent>) {
    CToolbar(
        navigation = {
            CIcon(imageVector = CapitalIcons.ArrowLeft, onClick = {
                viewModel.onEvent(TransactionManageEvent.BackClick)
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
        TransactionManageScreen(viewModel = TransactionManageMockViewModel())
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionManageScreenDark() {
    CPreview(isDark = true) {
        TransactionManageScreen(viewModel = TransactionManageMockViewModel())
    }
}

