package com.sweetmesoft.kmplibrary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform