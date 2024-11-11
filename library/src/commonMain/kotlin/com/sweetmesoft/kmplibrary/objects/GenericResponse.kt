package com.sweetmesoft.kmplibrary.objects

data class GenericResponse<T> (
    var obj: T,
    var cookies: List<String>,
    var headers: Map<String, List<String>>,
    var status: Int
)