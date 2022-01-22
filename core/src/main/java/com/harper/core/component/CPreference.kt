package com.harper.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalSwitchColors

@Composable
private fun CPreference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String?,
    action: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = if (subtitle == null) 16.dp else 8.dp)
    ) {
        Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)) {
            Text(text = title, style = CapitalTheme.typography.subtitle)
            subtitle?.let {
                Text(text = it, style = CapitalTheme.typography.regular, color = CapitalTheme.colors.textSecondary)
            }
        }
        Box(modifier = Modifier.align(Alignment.CenterVertically)) { action.invoke() }
    }
}

@Composable
fun CPreferenceArrow(modifier: Modifier = Modifier, title: String, subtitle: String? = null, onClick: () -> Unit) {
    CPreference(modifier.clickable { onClick.invoke() }, title = title, subtitle = subtitle) {
        Image(
            imageVector = CapitalIcons.ArrowRight,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
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
        modifier.clickable {
            isSwitchChecked.value = !isSwitchChecked.value
            onCheckedChange.invoke(isSwitchChecked.value)
        },
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

@Preview
@Composable
private fun SettingBoxLight() {
    CPreview {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CapitalTheme.colors.background)
        ) {
            CPreferenceArrow(
                modifier = Modifier.fillMaxWidth(),
                title = "Arrow setting",
                subtitle = "Use this setting"
            ) {}
            CPreferenceSwitch(
                modifier = Modifier.fillMaxWidth(),
                title = "Checkbox setting",
                subtitle = "Use this setting"
            ) {}
        }
    }
}

@Preview
@Composable
private fun SettingBoxDark() {
    CPreview(isDark = true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CapitalTheme.colors.background)
        ) {
            CPreferenceArrow(
                modifier = Modifier.fillMaxWidth(),
                title = "Arrow setting",
                subtitle = "Use this setting"
            ) {}
            CPreferenceSwitch(
                modifier = Modifier.fillMaxWidth(),
                title = "Checkbox setting",
                subtitle = "Use this setting"
            ) {}
        }
    }
}
