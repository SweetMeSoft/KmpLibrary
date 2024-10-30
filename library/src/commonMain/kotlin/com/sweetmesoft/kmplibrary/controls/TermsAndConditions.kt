package com.sweetmesoft.kmplibrary.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TermsAndConditions(
    modifier: Modifier = Modifier,
    urlPrivacy: String,
    urlTerms: String
) {
    val annotatedString = buildAnnotatedString {
        append("Al continuar estás aceptando los ")
        withLink(
            LinkAnnotation.Url(
                url = urlTerms,
                styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold))
            )
        ) {
            append("Términos y condiciones")
        }
        append(" del servicio y la ")
        withLink(
            LinkAnnotation.Url(
                url = urlPrivacy,
                styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold))
            )
        ) {
            append("Política de privacidad")
        }
        append(" de tus datos personales")
    }
    Box(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Text(
                text = annotatedString,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}