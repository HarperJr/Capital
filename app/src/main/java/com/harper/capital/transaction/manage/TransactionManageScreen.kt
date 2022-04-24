package com.harper.capital.transaction.manage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.harper.capital.transaction.manage.component.TransactionHeader
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.component.*
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDate

@Composable
fun TransactionManageScreen(
    viewModel: ComponentViewModel<TransactionManageState, TransactionManageEvent>
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    CScaffold(topBar = { TransactionManageTopBar(viewModel) }) {
        CLoaderLayout(isLoading = state.isLoading, loaderContent = {}) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = CapitalTheme.dimensions.side)
                ) {
                    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                    TransactionHeader(accounts = state.accounts)
                    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        CAmountTextField(
                            modifier = Modifier.weight(1f),
                            amount = state.exchangeState.sourceAmount,
                            placeholder = stringResource(id = R.string.enter_amount_hint),
                            currencyIso = state.exchangeState.sourceCurrency.name,
                            title = {
                                Text(text = stringResource(id = R.string.amount))
                            },
                            onValueChange = { viewModel.onEvent(TransactionManageEvent.SourceAmountChange(it)) },
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            })
                        )
                        if (state.exchangeState.hasExchange) {
                            CAmountTextField(
                                modifier = Modifier.weight(1f),
                                amount = state.exchangeState.receiverAmount,
                                placeholder = stringResource(id = R.string.enter_amount_hint),
                                currencyIso = state.exchangeState.receiverCurrency.name,
                                onValueChange = { viewModel.onEvent(TransactionManageEvent.TargetAmountChange(it)) },
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = {
                                    focusManager.clearFocus()
                                })
                            )
                        }
                    }
                    CHorizontalSpacer(height = CapitalTheme.dimensions.small)
                    if (state.exchangeState.hasExchange) {
                        Text(
                            text = stringResource(
                                id = R.string.exchange_hint,
                                state.exchangeState.rate.formatWithCurrencySymbol(state.exchangeState.sourceCurrency.name)
                            )
                        )
                    }
                    CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                    Text(text = stringResource(id = R.string.date))
                    CDatePicker(
                        dateStart = LocalDate.now().minusDays(30),
                        dateEnd = LocalDate.now(),
                        date = state.date.toLocalDate(),
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

