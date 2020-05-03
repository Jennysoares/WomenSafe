package br.com.victoriasantos.womensafe.domain

data class LocationData (
    var evaluation: String? = null,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var uid: String? = null
)