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
fun textStyleInter16Lh18Fw700(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.white),
        fontSize = 16.ssp,
        lineHeight = 18.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(700)
    )
}

@Composable
fun textStyleInter16Lh24Fw700(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.white),
        fontSize = 16.ssp,
        lineHeight = 24.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(700)
    )
}

@Composable
fun textStyleInter24Lh28Fw600(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.white),
        fontSize = 24.ssp,
        lineHeight = 28.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600)
    )
}

@Composable
fun textStyleInter28Lh34Fw600(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.white),
        fontSize = 28.ssp,
        lineHeight = 34.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600)
    )
}

@Composable
fun textStyleInter16Lh24Fw600(): TextStyle{
    return DefaultTextStyle.copy(
        fontSize = 16.ssp,
        lineHeight = 24.ssp,
        color = colorResource(id = R.color.white),
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600)
    )
}

@Composable
fun textStyleInter18Lh28Fw600(): TextStyle{
    return DefaultTextStyle.copy(
        fontSize = 18.ssp,
        lineHeight = 28.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600),
        color = colorResource(id = R.color.white)
    )
}

@Composable
fun textStyleInter32Lh38Fw600(): TextStyle{
    return DefaultTextStyle.copy(
        fontSize = 32.ssp,
        lineHeight = 38.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(600),
        color = colorResource(id = R.color.white)
    )
}

@Composable
fun textStyleInter14Lh20Fw500(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 14.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 20.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(500)
    )
}

@Composable
fun textStyleInter10Lh14Fw500(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 10.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 14.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(500)
    )
}


@Composable
fun textStyleInter16Lh24Fw500(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 16.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 24.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(500)
    )
}

@Composable
fun textStyleInter12Lh18Fw500(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 12.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 18.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(500)
    )
}

@Composable
fun textStyleInter12Lh16Fw500(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 12.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 16.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(500)
    )
}


@Composable
fun textStyleInter16Lh24Fw400(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 16.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 24.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(400)
    )
}

@Composable
fun textStyleInter14Lh20Fw400(): TextStyle {
    return DefaultTextStyle.copy(
        color = colorResource(id = R.color.white),
        fontSize = 14.ssp,
        lineHeight = 20.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(400)
    )
}

@Composable
fun textStyleInter12Lh18Fw400(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 12.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 18.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(400)
    )
}

@Composable
fun textStyleInter14Lh16Fw700(): TextStyle {
    return DefaultTextStyle.copy(
        fontSize = 14.ssp,
        color = colorResource(id = R.color.white),
        lineHeight = 16.ssp,
        fontFamily = FontFamily(Font(R.font.nunito_sans)),
        fontWeight = FontWeight(700)
    )
}