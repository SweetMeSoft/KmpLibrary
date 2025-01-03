package com.sweetmesoft.kmpcontrols

import com.sweetmesoft.kmpcontrols.controls.GeoPosition
import platform.MapKit.MKPointAnnotation

class CustomPointAnnotation(
    val geoPosition: GeoPosition,
) : MKPointAnnotation()