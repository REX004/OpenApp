package com.example.a01_wskpolice.Model.Data.session2.wanted

data class Wanted(
    val id: Int,
    val status: String,
    val first_name: String,
    val last_name: String,
    val last_location: String,
    val nicknames: String,
    val description: String,
    val photo: String,
    val isSelected: Boolean = false

)
