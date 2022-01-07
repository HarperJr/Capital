package com.harper.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.harper.core.R

private val Roboto: FontFamily = FontFamily(
    Font(R.font.roboto_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(R.font.roboto_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.roboto_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.roboto_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.roboto_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.roboto_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.roboto_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.roboto_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
    Font(R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic)
)

@Immutable
class CapitalTypography internal constructor(
    val header: TextStyle,
    val title: TextStyle,
    val titleSmall: TextStyle,
    val bottomSheetTitle: TextStyle,
    val subtitle: TextStyle,
    val regular: TextStyle,
    val regularSmall: TextStyle,
    val button: TextStyle,
    val buttonSmall: TextStyle

) {
    constructor(
        defaultFontFamily: FontFamily = Roboto,
        header: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            letterSpacing = 1.25.sp
        ),
        title: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            letterSpacing = 1.25.sp
        ),
        titleSmall: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.75.sp
        ),
        bottomSheetTitle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 0.75.sp
        ),
        subtitle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.75.sp
        ),
        regular: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.75.sp
        ),
        regularSmall: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 0.75.sp
        ),
        button: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 0.75.sp
        ),
        buttonSmall: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.45.sp
        )
    ) : this(
        header = header.withDefaultFontFamily(defaultFontFamily),
        title = title.withDefaultFontFamily(defaultFontFamily),
        titleSmall = titleSmall.withDefaultFontFamily(defaultFontFamily),
        bottomSheetTitle = bottomSheetTitle.withDefaultFontFamily(defaultFontFamily),
        subtitle = subtitle.withDefaultFontFamily(defaultFontFamily),
        regular = regular.withDefaultFontFamily(defaultFontFamily),
        regularSmall = regularSmall.withDefaultFontFamily(defaultFontFamily),
        button = button.withDefaultFontFamily(defaultFontFamily),
        buttonSmall = buttonSmall.withDefaultFontFamily(defaultFontFamily)
    )

    /**
     * Returns a copy of this Typography, optionally overriding some of the values.
     */
    fun copy(
        header: TextStyle = this.header,
        title: TextStyle = this.title,
        subtitle: TextStyle = this.subtitle,
        regular: TextStyle = this.regular,
        regularSmall: TextStyle = this.regularSmall,
        underline: TextStyle = this.button
    ): CapitalTypography = CapitalTypography(
        header = header,
        title = title,
        subtitle = subtitle,
        regular = regular,
        regularSmall = regularSmall,
        button = underline
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CapitalTypography) return false

        if (header != other.header) return false
        if (title != other.title) return false
        if (titleSmall != other.titleSmall) return false
        if (bottomSheetTitle != other.bottomSheetTitle) return false
        if (subtitle != other.subtitle) return false
        if (regular != other.regular) return false
        if (regularSmall != other.regularSmall) return false
        if (button != other.button) return false
        if (buttonSmall != other.buttonSmall) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + titleSmall.hashCode()
        result = 31 * result + bottomSheetTitle.hashCode()
        result = 31 * result + subtitle.hashCode()
        result = 31 * result + regular.hashCode()
        result = 31 * result + regularSmall.hashCode()
        result = 31 * result + button.hashCode()
        result = 31 * result + buttonSmall.hashCode()

        return result
    }

    override fun toString(): String {
        return "Typography(header=$header, title=$title, subtitle=$subtitle, regular=$regular, underline=$button"
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography = staticCompositionLocalOf { CapitalTypography() }