package com.example.a01_wskpolice.Model.Data.session3.criminalCase

data class CriminalCase(
    val id: String,
    val category: String,
    val detective: String,
    val client: String,
    val number: String,
    val description: String,
    val create_date: String,
    val images: List<Images>
)
