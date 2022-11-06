package com.harper.capital.transaction.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.imePadding
import com.harper.capital.R
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.transaction.manage.component.TransactionHeader
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CButtonPrimary
import com.harper.core.component.CDatePicker
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CLoaderLayout
import com.harper.core.component.CPreferenceSwitch
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CTextField
import com.harper.core.component.CToolbar
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDate

@Composable
fun TransactionManageScreen(
    viewModel: ComponentViewModel<TransactionManageState, TransactionManageEvent>,
    onBackClick: (() -> Unit)? = null
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    CScaffold(topBar = { TransactionManageTopBar(viewModel, state.mode, onBackClick) }) {
        CLoaderLayout(isLoading = state.isLoading, loaderContent = {}) {
            state.transaction?.let { transaction ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = CapitalTheme.dimensions.side)
                    ) {
                        CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                        TransactionHeader(transaction = transaction)
                        CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                        if (state.hasExchange) {
                            ExchangeBlock(viewModel, transaction, state.exchangeRate)
                        } else {
                            CAmountTextField(
                                modifier = Modifier.fillMaxWidth(),
                                amount = transaction.sourceAmount,
                                placeholder = stringResource(id = R.string.enter_amount_hint),
                                currencyIso = transaction.source.currency.name,
                                title = {
                                    Text(text = stringResource(id = R.string.amount))
                                },
                                onValueChange = { viewModel.onEvent(TransactionManageEvent.SourceAmountChange(it)) },
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = {
                                    focusManager.clearFocus()
                                })
                            )
                        }
                        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                        Text(text = stringResource(id = R.string.date))
                        CDatePicker(
                            dateStart = LocalDate.of(1970, 1, 1),
                            dateEnd = LocalDate.now(),
                            date = transaction.dateTime.toLocalDate(),
                            onDateSelect = { viewModel.onEvent(TransactionManageEvent.DateSelect(it)) }
                        )
                        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                        CTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = transaction.comment.orEmpty(),
                            placeholder = stringResource(id = R.string.enter_comment_hint),
                            title = { OptionalCommentTitle() },
                            onValueChange = { viewModel.onEvent(TransactionManageEvent.CommentChange(it)) }
                        )
                        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                        CPreferenceSwitch(
                            title = stringResource(id = R.string.schedule_transaction),
                            isChecked = transaction.isScheduled,
                            icon = {
                                Icon(imageVector = CapitalIcons.Calendar, contentDescription = null)
                            },
                            onCheckedChange = { viewModel.onEvent(TransactionManageEvent.ScheduledCheckChange(it)) }
                        )
                    }
                    val applyButtonText = when (state.mode) {
                        TransactionManageMode.ADD -> stringResource(id = R.string.create_new_transaction)
                        TransactionManageMode.EDIT -> stringResource(id = R.string.save)
                    }
                    CButtonPrimary(
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
}

@Composable
private fun ExchangeBlock(
    viewModel: ComponentViewModel<TransactionManageState, TransactionManageEvent>,
    transaction: TransferTransaction,
    exchangeRate: Double
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side),
        verticalAlignment = Alignment.Bottom
    ) {
        CAmountTextField(
            modifier = Modifier.weight(1f),
            amount = transaction.sourceAmount,
            placeholder = stringResource(id = R.string.enter_amount_hint),
            currencyIso = transaction.source.currency.name,
            title = {
                Text(text = stringResource(id = R.string.amount))
            },
            onValueChange = { viewModel.onEvent(TransactionManageEvent.SourceAmountChange(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        CAmountTextField(
            modifier = Modifier.weight(1f),
            amount = transaction.receiverAmount,
            placeholder = stringResource(id = R.string.enter_amount_hint),
            currencyIso = transaction.receiver.currency.name,
            onValueChange = { viewModel.onEvent(TransactionManageEvent.TargetAmountChange(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
    }
    CHorizontalSpacer(height = CapitalTheme.dimensions.small)
    Text(
        text = stringResource(
            id = R.string.exchange_hint,
            exchangeRate.formatWithCurrencySymbol(transaction.source.currency.name)
        )
    )
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
    viewModel: ComponentViewModel<TransactionManageState, TransactionManageEvent>,
    mode: TransactionManageMode,
    onBackClick: (() -> Unit)?
) {
    CToolbar(
        navigation = {
            when (mode) {
                TransactionManageMode.ADD -> {
                    CIcon(
                        imageVector = CapitalIcons.ArrowLeft,
                        onClick = onBackClick ?: { viewModel.onEvent(TransactionManageEvent.BackClick) }
                    )
                }
                TransactionManageMode.EDIT -> {
                    CIcon(
                        imageVector = CapitalIcons.ArrowLeft,
                        onClick = onBackClick ?: { viewModel.onEvent(TransactionManageEvent.BackClick) }
                    )
                }
            }
        }
    )
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

