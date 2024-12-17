package com.sweetmesoft.kmpmaps.objects

data class GeoPosition(
    val coordinates: Coordinates = Coordinates(),
    var address: String = "",
    val circleMap: CircleMap = CircleMap(),
    val markerMap: MarkerMap = MarkerMap()
)