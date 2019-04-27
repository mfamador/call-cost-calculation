package com.github.mfamador.callcost

import com.github.mfamador.callcost.exception.InvalidInputException
import com.github.mfamador.callcost.model.CallRecord
import java.io.File
import java.time.Duration
import java.time.LocalTime.parse

object CostCalculator {

    fun calculate(callLogFile: String) = calculate(File(callLogFile).useLines { it.toList() })

    fun calculate(callLog: List<String>): Double {
        val recordsByCaller = callLog.map { parseRecord(it) }.groupBy { it.from }
        val durations = recordsByCaller.map { it.key to it.value.sumByDouble { it.duration.toDouble() } }
        val maxDuration = durations.maxBy { it.second }?.second
        val callersNotCharged = durations.filter { it.second == maxDuration }.map { it.first }

        return recordsByCaller.map { it.key to it.value.sumByDouble { it.cost.toDouble() } }
                .filter { !callersNotCharged.contains(it.first) }.map { it.second }.sum().div(100)
    }

    private fun parseRecord(callLog: String): CallRecord {
        val elements = callLog.trim().split(";")
        if (elements.size != 4) throw InvalidInputException("Invalid input file")
        return CallRecord(getDuration(elements[0], elements[1]), elements[2], elements[3])
    }

    private fun getDuration(start: String, end: String) = Duration.between(parse(start), parse(end)).seconds
}
