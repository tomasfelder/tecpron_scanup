package com.tecpron.tecpronscanning.data.model
import java.util.*

data class Station(
    val id: Int,
    val date: Date,
    val cancelled: Boolean,
    val state: Int,
    val complexity: Complexity
)