package ree.selfcode.localbrew.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LocalBrewColorScheme = lightColorScheme(
    primary = Accent,
    onPrimary = Surface,
    primaryContainer = Surface,
    onPrimaryContainer = Accent,
    secondary = Accent,
    onSecondary = Surface,
    secondaryContainer = Surface,
    onSecondaryContainer = Accent,
    tertiary = Accent,
    onTertiary = Surface,
    background = Background,
    onBackground = TextColor,
    surface = Surface,
    onSurface = TextColor,
    surfaceVariant = Surface,
    onSurfaceVariant = TextColor,
    outline = SurfaceBorder
)

private val LocalBrewShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(14.dp),
    extraLarge = RoundedCornerShape(16.dp)
)

@Composable
fun LocalbrewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is off by default so the custom brand palette below is never overridden
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> LocalBrewColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = LocalBrewShapes,
        content = content
    )
}
