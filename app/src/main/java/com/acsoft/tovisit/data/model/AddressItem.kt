package com.acsoft.tovisit.data.model

data class AddressItem(
    val id : Int,
    val streetName: String,
    val suburb: String,
    val visited: Boolean,
    val location: Location,
)