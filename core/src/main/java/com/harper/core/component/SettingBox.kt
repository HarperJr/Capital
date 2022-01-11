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
fun SettingBox(modifier: Modifier = Modifier, title: String, subtitle: String, action: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .background(color = CapitalTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f, fill = true)) {
            Text(text = title, style = CapitalTheme.typography.subtitle, color = CapitalTheme.colors.onBackground)
            Text(text = subtitle, style = CapitalTheme.typography.regular, color = CapitalTheme.colors.onSecondary)
        }
        Box(modifier = Modifier.align(Alignment.CenterVertically)) { action.invoke() }
    }
}

@Composable
fun ArrowSettingBox(modifier: Modifier, title: String, subtitle: String, onClick: () -> Unit) {
    SettingBox(modifier.clickable { onClick.invoke() }, title = title, subtitle = subtitle) {
        Image(
            imageVector = CapitalIcons.ArrowRight,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
        )
    }
}


@Composable
fun SwitchSettingBox(
    modifier: Modifier,
    title: String,
    subtitle: String,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    val isSwitchChecked = remember(isChecked) { mutableStateOf(isChecked) }
    SettingBox(
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
    ComposablePreview {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CapitalTheme.colors.background)
        ) {
            ArrowSettingBox(
                modifier = Modifier.fillMaxWidth(),
                title = "Arrow setting",
                subtitle = "Use this setting"
            ) {}
            SwitchSettingBox(
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
    ComposablePreview(isDark = true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CapitalTheme.colors.background)
        ) {
            ArrowSettingBox(
                modifier = Modifier.fillMaxWidth(),
                title = "Arrow setting",
                subtitle = "Use this setting"
            ) {}
            SwitchSettingBox(
                modifier = Modifier.fillMaxWidth(),
                title = "Checkbox setting",
                subtitle = "Use this setting"
            ) {}
        }
    }
}
