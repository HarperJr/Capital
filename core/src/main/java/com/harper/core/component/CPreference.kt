package com.harper.core.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalSwitchColors

private val preferenceMinHeight = 64.dp

@Composable
private fun CPreference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String?,
    action: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = preferenceMinHeight)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = CapitalTheme.typography.subtitle, overflow = TextOverflow.Ellipsis)
            subtitle?.let {
                Text(text = it, style = CapitalTheme.typography.regular, color = CapitalTheme.colors.textSecondary)
            }
        }
        Box { action.invoke() }
    }
}

@Composable
fun CPreferenceArrow(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    CPreference(
        Modifier
            .clickable { onClick.invoke() }
            .then(modifier),
        title = title,
        subtitle = subtitle
    ) {
        Icon(
            imageVector = CapitalIcons.ArrowRight,
            contentDescription = null,
            tint = CapitalTheme.colors.onBackground
        )
    }
}


@Composable
fun CPreferenceSwitch(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    val isSwitchChecked = remember(isChecked) { mutableStateOf(isChecked) }
    CPreference(
        Modifier
            .clickable {
                isSwitchChecked.value = !isSwitchChecked.value
                onCheckedChange.invoke(isSwitchChecked.value)
            }
            .then(modifier),
        title = title,
        subtitle = subtitle
    ) {
        Switch(
            checked = isSwitchChecked.value,
            onCheckedChange = {
                isSwitchChecked.value = !isSwitchChecked.value
                onCheckedChange.invoke(it)
            },
            colors = capitalSwitchColors()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingBoxLight() {
    CPreview {
        Column {
            CPreferenceArrow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.side),
                title = "Arrow setting",
                subtitle = "Use this setting"
            ) {}
            CPreferenceSwitch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.side),
                title = "Checkbox setting",
                subtitle = "Use this setting"
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingBoxDark() {
    CPreview(isDark = true) {
        Column {
            CPreferenceArrow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.side),
                title = "Arrow setting",
                subtitle = "Use this setting"
            ) {}
            CPreferenceSwitch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.side),
                title = "Checkbox setting",
                subtitle = "Use this setting"
            ) {}
        }
    }
}
