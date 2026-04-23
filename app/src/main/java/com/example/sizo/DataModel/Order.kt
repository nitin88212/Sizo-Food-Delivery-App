package com.example.sizo.DataModel


data class Order(
    val items: List<Map<String, Any>> = emptyList(),
    val total: Int = 0,
    val timestamp: Long = 0,
    val status: String = "Placed"
)
