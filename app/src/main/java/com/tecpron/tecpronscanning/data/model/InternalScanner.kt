package com.tecpron.tecpronscanning.data.model

data class InternalScanner (val id: String, val name: String) {
    override fun toString(): String {
        return name
    }
}