package com.app.secanalyst.model

data class IpInfo(
    val ip: String,
    val mask: String,
    val gateway: String
) {
    fun toCardItems(): List<Pair<String, String>> = listOf(
        "IP 地址" to ip,
        "子网掩码" to mask,
        "网关" to gateway
    )
}