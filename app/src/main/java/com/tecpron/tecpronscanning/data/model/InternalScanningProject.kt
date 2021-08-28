package com.tecpron.tecpronscanning.data.model

import com.amplifyframework.core.model.temporal.Temporal
import java.util.*

data class InternalScanningProject(val id:String, val name:String, val startDate:String , val stationsNumber:Int, val scanner:InternalScanner) {
    override fun toString(): String {
        return name
    }
}

