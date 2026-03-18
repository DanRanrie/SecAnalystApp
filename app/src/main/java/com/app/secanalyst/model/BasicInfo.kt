package com.app.secanalyst.model

data class BasicInfo(
    val brand: String,
    val model: String,
    val androidVersion: String,
    val apiLevel: Int,
    val arch: String,
    val uptime: String,
    val romReleaseDate: String
)
