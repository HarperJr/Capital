package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalSwitchColors

private val preferenceMinHeight = 56.dp

@Composable
private fun CPreference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String?,
    icon: (@Composable BoxScope.() -> Unit)? = null,
    action: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = preferenceMinHeight)
            .background(color = CapitalTheme.colors.primaryVariant, shape = CapitalTheme.shapes.large)
            .clip(shape = CapitalTheme.shapes.large)
            .padding(horizontal = CapitalTheme.dimensions.side),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalContentColor provides CapitalColors.White) {
            icon?.let {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(color = CapitalTheme.colors.secondary, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    it.invoke(this)
                }
                CVerticalSpacer(width = CapitalTheme.dimensions.medium)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = CapitalTheme.typography.subtitle, overflow = TextOverflow.Ellipsis)
            subtitle?.let {
                Text(text = it, style = CapitalTheme.typography.regularSmall, color = CapitalTheme.colors.textSecondary)
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
    icon: (@Composable BoxScope.() -> Unit)? = null,
    onClick: () -> Unit
) {
    CPreference(
        modifier.clickable { onClick.invoke() },
        title = title,
        subtitle = subtitle,
        icon = icon
    ) {
        Icon(
            imageVector = CapitalIcons.ArrowRight,
            contentDescription = null,
            tint = CapitalTheme.colors.textSecondary
        )
    }
}


@Composable
fun CPreferenceSwitch(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    isChecked: Boolean = false,
    icon: (@Composable BoxScope.() -> Unit)? = null,
    onCheckedChange: (Boolean) -> Unit
) {
    val isSwitchChecked = remember(isChecked) { mutableStateOf(isChecked) }
    CPreference(
        modifier.clickable {
            isSwitchChecked.value = !isSwitchChecked.value
            onCheckedChange.invoke(isSwitchChecked.value)
        },
        title = title,
        subtitle = subtitle,
        icon = icon
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
    CPreview(isDark = false) {
        SettingsBoxPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingBoxDark() {
    CPreview(isDark = true) {
        SettingsBoxPreview()
    }
}

@Composable
private fun SettingsBoxPreview() {
    Column {
        CPreferenceArrow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CapitalTheme.dimensions.side),
            title = "Arrow setting",
            subtitle = "Use this setting",
            icon = {
                Icon(imageVector = CapitalIcons.Calendar, contentDescription = null)
            }
        ) {}
        CPreferenceSwitch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CapitalTheme.dimensions.side),
            title = "Checkbox setting",
            subtitle = "Use this setting"
        ) {}
    }
}
