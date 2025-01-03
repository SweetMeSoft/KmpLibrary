package com.sweetmesoft.kmpmaps

import com.sweetmesoft.kmpmaps.controls.GeoPosition
import platform.MapKit.MKPointAnnotation

class CustomPointAnnotation(
    val geoPosition: GeoPosition,
) : MKPointAnnotation()