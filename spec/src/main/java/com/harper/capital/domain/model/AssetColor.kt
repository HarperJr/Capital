package com.harper.capital.domain.model

enum class AssetColor(val value: Long) {
    DARK_TINKOFF(0xFF231F20),
    GREEN_SBER(0xFF48B356),
    RED_ALPHA(0xFFEE3124),
    BLUE_VTB(0xFF244480),
    LIGHT_BLUE_VTB(0xFF1ABAEF),
    YELLOW_RAIFFEIZEN(0xFFFDF41F);

    companion object {

        fun valueOf(value: Long): AssetColor = values().find { it.value == value }
            ?: throw IllegalArgumentException("Unable to find color with value = $value")
    }
}