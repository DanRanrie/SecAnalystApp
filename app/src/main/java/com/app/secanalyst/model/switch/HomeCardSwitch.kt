package com.app.secanalyst.model.switch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object HomeCardSwitch {
    var ip by mutableStateOf(true)
    var basic by mutableStateOf(true)
    var screen by mutableStateOf(true)
    var network by mutableStateOf(true)
    var memory by mutableStateOf(true)
    var storage by mutableStateOf(true)
    var battery by mutableStateOf(true)

    fun isEnabled(cardId: String): Boolean = when (cardId) {
        "ip" -> ip
        "basic" -> basic
        "screen" -> screen
        "network" -> network
        "memory" -> memory
        "storage" -> storage
        "battery" -> battery
        else -> true
    }
}
