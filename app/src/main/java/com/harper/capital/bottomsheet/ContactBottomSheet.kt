package com.harper.capital.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.harper.capital.R
import com.harper.capital.domain.model.Contact
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.component.CSeparator
import com.harper.core.component.CTextField
import com.harper.core.component.CVerticalSpacer
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun ContactBottomSheet(
    modifier: Modifier = Modifier,
    contacts: List<Contact>,
    selectedContact: Contact?,
    onContactSelect: (Contact) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val filteredContacts = remember(contacts, searchQuery.value) {
        contacts.filter {
            searchQuery.value.isEmpty() ||
                    it.name.contains(searchQuery.value, ignoreCase = true)
        }
    }
    Column(modifier = modifier.fillMaxWidth()) {
        CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
        CTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side),
            value = searchQuery.value,
            placeholder = stringResource(id = R.string.search),
            leadingIcon = {
                Image(
                    modifier = Modifier.padding(end = CapitalTheme.dimensions.medium),
                    imageVector = CapitalIcons.Search,
                    colorFilter = ColorFilter.tint(color = CapitalColors.GreyDark),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            onValueChange = { searchQuery.value = it }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(CapitalTheme.dimensions.side)
        )
        CSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredContacts) {
                ContactItem(contact = it, isSelected = it == selectedContact) {
                    onContactSelect.invoke(it)
                }
            }
        }
    }
}

@Composable
private fun ContactItem(modifier: Modifier = Modifier, contact: Contact, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .padding(CapitalTheme.dimensions.side)
            .fillMaxWidth()
    ) {
        val textStyle = if (isSelected) {
            CapitalTheme.typography.regular.copy(fontWeight = FontWeight.Bold)
        } else {
            CapitalTheme.typography.regular
        }
        val textColor = if (isSelected) CapitalColors.Blue else CapitalTheme.colors.onBackground

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onClick.invoke() }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(CapitalTheme.dimensions.imageMedium)
                    .background(color = CapitalTheme.colors.primaryVariant, shape = CircleShape),
                painter = rememberImagePainter(data = contact.avatar) {
                    this
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                },
                contentDescription = null
            )
            CVerticalSpacer(width = CapitalTheme.dimensions.side)
            Column {
                Text(
                    text = contact.name,
                    style = textStyle,
                    color = textColor
                )
                CHorizontalSpacer(height = CapitalTheme.dimensions.small)
                Text(
                    text = contact.phone.orEmpty(),
                    style = CapitalTheme.typography.regularSmall,
                    color = textColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactBottomSheetLight() {
    CPreview(isDark = false) {
        ContactBottomSheetPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactBottomSheetDark() {
    CPreview(isDark = true) {
        ContactBottomSheetPreview()
    }
}

@Composable
private fun ContactBottomSheetPreview() {
    val contacts = listOf(
        Contact(name = "Alex Simon", phone = "+1(999)-124-41-24", avatar = ""),
        Contact(name = "Damon Anderson", phone = "+1(173)-288-55-19", avatar = ""),
        Contact(name = "Kianu Reeves", phone = "+1(424)-466-48-02", avatar = ""),
        Contact(name = "Solomon K", phone = "+1(521)-644-42-12", avatar = "")
    )
    ContactBottomSheet(contacts = contacts, selectedContact = contacts[2]) {}
}
