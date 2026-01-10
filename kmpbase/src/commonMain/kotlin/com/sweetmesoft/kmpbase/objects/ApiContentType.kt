package com.sweetmesoft.kmpbase.objects

import io.ktor.http.ContentType

enum class ApiContentType(val contentType: ContentType) {
    Json(ContentType.Application.Json),
    Xml(ContentType.Application.Xml),
    FormUrlEncoded(ContentType.Application.FormUrlEncoded),
    Text(ContentType.Text.Plain),
    Html(ContentType.Text.Html),
    Css(ContentType.Text.CSS),
    JavaScript(ContentType.Text.JavaScript),
    Pdf(ContentType.Application.Pdf),
    OctetStream(ContentType.Application.OctetStream)
}
