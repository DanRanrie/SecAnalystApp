package com.app.secanalyst.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

enum class SecAnalystPalette {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    CYAN,
    BLUE,
    PURPLE,
    NEUTRAL,
    BROWN
}

data class PaletteSchemeHolder(
    val dark: ColorScheme,
    val light: ColorScheme
)

object PaletteScheme {
    fun seedColor(palette: SecAnalystPalette): Color = when (palette) {
        SecAnalystPalette.RED -> Red40
        SecAnalystPalette.ORANGE -> Orange40
        SecAnalystPalette.YELLOW -> Yellow40
        SecAnalystPalette.GREEN -> Green40
        SecAnalystPalette.CYAN -> Cyan40
        SecAnalystPalette.BLUE -> Blue40
        SecAnalystPalette.PURPLE -> Purple40
        SecAnalystPalette.NEUTRAL -> Gray40
        SecAnalystPalette.BROWN -> Brown40
    }

    fun from(palette: SecAnalystPalette): PaletteSchemeHolder = when (palette) {
        SecAnalystPalette.RED -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Red80,
                secondary = RedSecondary80,
                tertiary = RedTertiary80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Red40,
                secondary = RedSecondary40,
                tertiary = RedTertiary40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.ORANGE -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Orange80,
                secondary = OrangeSecondary80,
                tertiary = OrangeTertiary80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Orange40,
                secondary = OrangeSecondary40,
                tertiary = OrangeTertiary40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.YELLOW -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Yellow80,
                secondary = YellowSecondary80,
                tertiary = YellowTertiary80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Yellow40,
                secondary = YellowSecondary40,
                tertiary = YellowTertiary40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.GREEN -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Green80,
                secondary = GreenSecondary80,
                tertiary = GreenTertiary80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Green40,
                secondary = GreenSecondary40,
                tertiary = GreenTertiary40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.CYAN -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Cyan80,
                secondary = CyanSecondary80,
                tertiary = Teal80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Cyan40,
                secondary = CyanSecondary40,
                tertiary = Teal40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.BLUE -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Blue80,
                secondary = BlueSecondary80,
                tertiary = BlueTertiary80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Blue40,
                secondary = BlueSecondary40,
                tertiary = BlueTertiary40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.PURPLE -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Purple80,
                secondary = PurpleGrey80,
                tertiary = Pink80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Purple40,
                secondary = PurpleGrey40,
                tertiary = Pink40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.NEUTRAL -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = White80,
                secondary = Gray80,
                tertiary = Gray80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Gray40,
                secondary = Gray40,
                tertiary = Gray40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
        SecAnalystPalette.BROWN -> PaletteSchemeHolder(
            dark = darkColorScheme(
                primary = Brown80,
                secondary = Tan80,
                tertiary = Chocolate80,
                background = BackgroundDark,
                surface = SurfaceDark,
                surfaceContainer = SurfaceContainerDark,
                surfaceContainerHigh = SurfaceContainerHighDark,
                outline = OutlineDark,
                surfaceVariant = SurfaceVariantDark,
                onPrimary = OnPrimaryDark,
                error = Error80
            ),
            light = lightColorScheme(
                primary = Brown40,
                secondary = Tan40,
                tertiary = Chocolate40,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceContainer = SurfaceContainerLight,
                surfaceContainerHigh = SurfaceContainerHighLight,
                outline = OutlineLight,
                surfaceVariant = SurfaceVariantLight,
                onPrimary = OnPrimaryLight,
                error = Error40
            )
        )
    }
}
