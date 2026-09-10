package org.ok1cdj.kchat.ui.theme

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private val black = Color(0xFF000000)
private val white = Color(0xFFFFFFFF)

// Strictly black/white for the 1-bit e-ink panel of the Mudita Kompakt. MMD and
// stock M3 components read MaterialTheme.colorScheme, so every role — including the
// *Container roles — is coerced to black/white; otherwise the Material purple
// baseline and gray fills leak through and dither badly on the display.
// (Same pattern as the sibling Kompakt apps kRadar / kSread.)
private val EinkColors = lightColorScheme(
    primary = black,
    onPrimary = white,
    primaryContainer = white,
    onPrimaryContainer = black,
    secondary = black,
    onSecondary = white,
    secondaryContainer = white,
    onSecondaryContainer = black,
    tertiary = black,
    onTertiary = white,
    tertiaryContainer = white,
    onTertiaryContainer = black,
    background = white,
    onBackground = black,
    surface = white,
    onSurface = black,
    surfaceVariant = white,
    onSurfaceVariant = black,
    // The M3 popups (DropdownMenu, AlertDialog, ModalBottomSheet) read the
    // surfaceContainer* tonal roles, which default to light gray. Force them white
    // so every popup is pure white with the black borders/dividers we draw.
    surfaceContainerLowest = white,
    surfaceContainerLow = white,
    surfaceContainer = white,
    surfaceContainerHigh = white,
    surfaceContainerHighest = white,
    surfaceBright = white,
    surfaceDim = white,
    surfaceTint = white,
    inverseSurface = black,
    inverseOnSurface = white,
    outline = black,
    outlineVariant = black,
    error = black,
    onError = white,
)

private val AppTypography = Typography(
    titleLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.2).sp),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.1).sp),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.4.sp)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KChatTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            val insets = WindowCompat.getInsetsController(window, view)
            // Single e-ink scheme is always light (black on white).
            insets.isAppearanceLightStatusBars = true
            insets.isAppearanceLightNavigationBars = true
            // Hide the status bar: on the Kompakt's short e-ink panel the system
            // status bar just wastes vertical space at the top of every screen.
            // Swipe from the top still reveals it transiently.
            insets.hide(WindowInsetsCompat.Type.statusBars())
            insets.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    // Disable ripple globally: e-ink panels can't render the fade, and it just
    // causes distracting partial redraws. MMD components already suppress it;
    // this covers the stock M3 widgets kChat keeps (Switch/Slider/menus/rows).
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        MaterialTheme(
            colorScheme = EinkColors,
            typography = AppTypography,
            content = content
        )
    }
}
