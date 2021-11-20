package com.harper.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
class CapitalTypography internal constructor(
    val header: TextStyle,
    val title: TextStyle,
    val subtitle: TextStyle,
    val regular: TextStyle,
    val underline: TextStyle
) {
    constructor(
        defaultFontFamily: FontFamily = FontFamily.Monospace,
        header: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            letterSpacing = 0.sp
        ),
        title: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        regular: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        ),
        underline: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.1.sp
        )
    ) : this(
        header = header.withDefaultFontFamily(defaultFontFamily),
        title = title.withDefaultFontFamily(defaultFontFamily),
        subtitle = subtitle.withDefaultFontFamily(defaultFontFamily),
        regular = regular.withDefaultFontFamily(defaultFontFamily),
        underline = underline.withDefaultFontFamily(defaultFontFamily)
    )

    /**
     * Returns a copy of this Typography, optionally overriding some of the values.
     */
    fun copy(
        header: TextStyle = this.header,
        title: TextStyle = this.title,
        subtitle: TextStyle = this.subtitle,
        regular: TextStyle = this.regular,
        underline: TextStyle = this.underline
    ): CapitalTypography = CapitalTypography(
        header = header,
        title = title,
        subtitle = subtitle,
        regular = regular,
        underline = underline
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CapitalTypography) return false

        if (header != other.header) return false
        if (title != other.title) return false
        if (subtitle != other.subtitle) return false
        if (regular != other.regular) return false
        if (underline != other.underline) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + subtitle.hashCode()
        result = 31 * result + regular.hashCode()
        result = 31 * result + underline.hashCode()

        return result
    }

    override fun toString(): String {
        return "Typography(header=$header, title=$title, subtitle=$subtitle, regular=$regular, underline=$underline"
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography = staticCompositionLocalOf { CapitalTypography() }