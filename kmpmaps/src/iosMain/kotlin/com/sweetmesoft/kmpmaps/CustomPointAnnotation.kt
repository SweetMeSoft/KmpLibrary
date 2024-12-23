package com.sweetmesoft.kmpmaps

import com.sweetmesoft.kmpmaps.objects.MarkerMap
import platform.MapKit.MKPointAnnotation
import platform.UIKit.UIColor

class CustomPointAnnotation(
    val color: UIColor,
    val onClick: (MarkerMap) -> Boolean,
    val onInfoWindowClick: () -> Unit
) : MKPointAnnotation()