package com.example.stride.utility.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.stride.R
import com.example.stride.utility.composeUtility.ssp

val DefaultTextStyle = TextStyle(
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

@Composable
fun textStyleInter16Lh28Fw700(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.font_600),
        fontSize = 16.ssp,
        lineHeight = 18.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(700)
    )
}

@Composable
fun textStyleInter16Lh28Fw600(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.font_600),
        fontSize = 24.ssp,
        lineHeight = 28.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600)
    )
}

@Composable
fun textStyleInter16Lh28Fw500(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.font_500),
        fontSize = 16.ssp,
        lineHeight = 24.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600)
    )
}

@Composable
fun textStyleInter16Lh28Fw400(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.font_600),
        fontSize = 24.ssp,
        lineHeight = 28.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(400)
    )
}
