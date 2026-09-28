package ree.selfcode.localbrew.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import ree.selfcode.localbrew.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val frauncesFont = GoogleFont("Fraunces")
private val sourceSans3Font = GoogleFont("Source Sans 3")
private val ibmPlexMonoFont = GoogleFont("IBM Plex Mono")

val FrauncesFamily = FontFamily(
    Font(googleFont = frauncesFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = frauncesFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = frauncesFont, fontProvider = provider, weight = FontWeight.Bold)
)

val SourceSans3Family = FontFamily(
    Font(googleFont = sourceSans3Font, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = sourceSans3Font, fontProvider = provider, weight = FontWeight.Bold)
)

val IBMPlexMonoFamily = FontFamily(
    Font(googleFont = ibmPlexMonoFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = ibmPlexMonoFont, fontProvider = provider, weight = FontWeight.SemiBold)
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.Bold, fontSize = 36.sp, lineHeight = 42.sp),
    headlineLarge = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 34.sp),
    headlineMedium = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 30.sp),
    titleLarge = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 26.sp),
    titleMedium = TextStyle(fontFamily = SourceSans3Family, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 22.sp),
    bodyLarge = TextStyle(fontFamily = SourceSans3Family, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontFamily = SourceSans3Family, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontFamily = SourceSans3Family, fontWeight = FontWeight.Bold, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    labelSmall = TextStyle(fontFamily = SourceSans3Family, fontWeight = FontWeight.Normal, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp)
)
