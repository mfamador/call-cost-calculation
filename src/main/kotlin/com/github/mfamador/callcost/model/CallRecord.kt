package com.github.mfamador.callcost.model

import java.lang.Math.ceil

data class CallRecord(val duration: Long, val from: String, val to: String) {

    val cost: Long

    init {
        val minutes = ceil(duration.toDouble() / 60).toLong()
        cost = if (minutes <= 5)
            minutes * 5
        else
            (minutes-5) * 2 + (5 * 5)
    }

    override fun toString(): String = "(duration=$duration, cost=$cost , from=$from , to=$to)"
}
