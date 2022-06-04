package com.harper.capital.liability

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.bottomsheet.ContactBottomSheet
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.ext.getImageVector
import com.harper.capital.liability.model.LiabilityManageBottomSheet
import com.harper.capital.liability.model.LiabilityManageEvent
import com.harper.capital.liability.model.LiabilityManagePage
import com.harper.capital.liability.model.LiabilityManageState
import com.harper.capital.liability.model.LiabilityManageType
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CModalBottomSheetScaffold
import com.harper.core.component.CButton
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreview
import com.harper.core.component.CTabBarCommon
import com.harper.core.component.CTextField
import com.harper.core.component.CToolbar
import com.harper.core.ext.compose.MaskVisualTransformation
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.ext.orElse
import com.harper.core.ext.tryCast
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel

private const val PHONE_MASK = "+?(???)-???-??-??"
private const val PHONE_LENGTH = 11

@Composable
@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
fun LiabilityManageScreen(
    viewModel: ComponentViewModel<LiabilityManageState, LiabilityManageEvent>
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val focusManager = LocalFocusManager.current

    CModalBottomSheetScaffold(
        sheetContent = {
            val bottomSheet = remember(state.bottomSheetState) {
                state.bottomSheetState.bottomSheet
            }
            BottomSheetContent(bottomSheet, viewModel)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    focusManager.clearFocus(force = true)
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        topBar = { LiabilityManageTopBar(viewModel) },
        bottomBar = {
            CButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CapitalTheme.dimensions.side),
                text = stringResource(id = R.string.create_new_liability),
                onClick = { viewModel.onEvent(LiabilityManageEvent.Apply) }
            )
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val pagerState = rememberPagerState(initialPage = state.selectedPage)
            if (state.pages.size > 1) {
                CTabBarCommon(
                    data = state.tabBarData,
                    pagerState = pagerState,
                    onTabSelect = { viewModel.onEvent(LiabilityManageEvent.TabSelect(it)) }
                )
            }
            HorizontalPager(state = pagerState, count = state.pages.size) { pageIndex ->
                val page = state.pages[pageIndex]
                if (page.type == LiabilityManageType.DEBT) {
                    DebtPageBlock(page, viewModel)
                } else {
                    PageBlock(page = page, viewModel)
                }
            }
        }
    }
}

@Composable
private fun DebtPageBlock(page: LiabilityManagePage, viewModel: ComponentViewModel<LiabilityManageState, LiabilityManageEvent>) {
    val focusManager = LocalFocusManager.current
    val metadata = page.metadata.tryCast<AccountMetadata.Debt>()
    val contactsPermissionRequester = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onEvent(LiabilityManageEvent.ContactSelectClick)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CHorizontalSpacer(height = CapitalTheme.dimensions.largest)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side)
        ) {
            Box(
                modifier = Modifier
                    .size(CapitalTheme.dimensions.imageLarge)
                    .background(color = CapitalTheme.colors.primaryVariant, shape = CircleShape)
                    .clickable { contactsPermissionRequester.launch(Manifest.permission.READ_CONTACTS) }
            ) {
                metadata?.avatar?.let {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = rememberImagePainter(data = it),
                        contentDescription = null
                    )
                }.orElse {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = page.icon.getImageVector(),
                        contentDescription = null
                    )
                }
            }
            CTextField(
                modifier = Modifier
                    .padding(start = CapitalTheme.dimensions.side)
                    .align(Alignment.CenterVertically),
                value = page.name,
                placeholder = stringResource(id = R.string.enter_name_hint),
                onValueChange = {
                    viewModel.onEvent(LiabilityManageEvent.NameChange(it))
                },
                textColor = CapitalTheme.colors.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
        }
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        CTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side),
            value = metadata?.phone.orEmpty(),
            onValueChange = {
                if (it.length <= PHONE_LENGTH) {
                    viewModel.onEvent(LiabilityManageEvent.PhoneChange(it))
                }
            },
            textColor = CapitalTheme.colors.onBackground,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            visualTransformation = MaskVisualTransformation(mask = PHONE_MASK)
        )
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        CAmountTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side),
            amount = page.amount,
            currencyIso = page.currency.name,
            placeholder = stringResource(id = R.string.enter_amount_hint),
            onValueChange = {
                viewModel.onEvent(LiabilityManageEvent.AmountChange(it))
            },
            textColor = CapitalTheme.colors.onBackground,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        CPreferenceArrow(
            modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side),
            title = stringResource(id = R.string.currency),
            subtitle = page.currency.name.formatCurrencyName(),
            icon = {
                Text(
                    text = page.currency.name.formatCurrencySymbol(),
                    style = CapitalTheme.typography.title,
                    textAlign = TextAlign.Center
                )
            },
            onClick = { viewModel.onEvent(LiabilityManageEvent.CurrencySelectClick) }
        )
    }
}

@Composable
fun PageBlock(page: LiabilityManagePage, viewModel: ComponentViewModel<LiabilityManageState, LiabilityManageEvent>) {
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        CHorizontalSpacer(height = CapitalTheme.dimensions.largest)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side)
        ) {
            Box(
                modifier = Modifier
                    .size(CapitalTheme.dimensions.imageLarge)
                    .background(color = CapitalTheme.colors.primaryVariant, shape = CircleShape)
                    .clickable { viewModel.onEvent(LiabilityManageEvent.IconSelectClick) }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = page.icon.getImageVector(),
                    contentDescription = null
                )
            }
            CTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = CapitalTheme.dimensions.side)
                    .align(Alignment.CenterVertically),
                value = page.name,
                placeholder = stringResource(id = R.string.enter_name_hint),
                onValueChange = {
                    viewModel.onEvent(LiabilityManageEvent.NameChange(it))
                },
                textColor = CapitalTheme.colors.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
        }
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        CAmountTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side),
            amount = page.amount,
            currencyIso = page.currency.name,
            placeholder = stringResource(id = R.string.enter_amount_hint),
            onValueChange = {
                viewModel.onEvent(LiabilityManageEvent.AmountChange(it))
            },
            textColor = CapitalTheme.colors.onBackground,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        CPreferenceArrow(
            modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side),
            title = stringResource(id = R.string.currency),
            subtitle = page.currency.name.formatCurrencyName(),
            icon = {
                Text(
                    text = page.currency.name.formatCurrencySymbol(),
                    style = CapitalTheme.typography.title,
                    textAlign = TextAlign.Center
                )
            },
            onClick = { viewModel.onEvent(LiabilityManageEvent.CurrencySelectClick) }
        )
    }
}

@Composable
private fun BottomSheetContent(
    bottomSheet: LiabilityManageBottomSheet?,
    viewModel: ComponentViewModel<LiabilityManageState, LiabilityManageEvent>
) {
    when (bottomSheet) {
        is LiabilityManageBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { viewModel.onEvent(LiabilityManageEvent.CurrencySelect(it)) }
            )
        }
        is LiabilityManageBottomSheet.Icons -> {
            IconsBottomSheet(
                data = bottomSheet.data,
                onIconSelect = { viewModel.onEvent(LiabilityManageEvent.IconSelect(it)) }
            )
        }
        is LiabilityManageBottomSheet.Contacts -> {
            ContactBottomSheet(
                contacts = bottomSheet.contacts,
                selectedContact = bottomSheet.selectedContact,
                onContactSelect = { viewModel.onEvent(LiabilityManageEvent.ContactSelect(it)) }
            )
        }
        else -> {
        }
    }
}

@Composable
private fun LiabilityManageTopBar(viewModel: ComponentViewModel<LiabilityManageState, LiabilityManageEvent>) {
    CToolbar(
        content = {
            Text(
                modifier = Modifier.padding(start = CapitalTheme.dimensions.side),
                text = stringResource(id = R.string.new_liability),
                style = CapitalTheme.typography.title
            )
        },
        navigation = {
            CIcon(
                imageVector = CapitalIcons.ArrowLeft,
                onClick = { viewModel.onEvent(LiabilityManageEvent.BackClick) }
            )
        }
    )
}

@Preview
@Composable
private fun LiabilityManageScreenLight() {
    CPreview {
        LiabilityManageScreen(LiabilityManageMockViewModel())
    }
}

@Preview
@Composable
private fun LiabilityManageScreenDark() {
    CPreview(isDark = true) {
        LiabilityManageScreen(LiabilityManageMockViewModel())
    }
}
