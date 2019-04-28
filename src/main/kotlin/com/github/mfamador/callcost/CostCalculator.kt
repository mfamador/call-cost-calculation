package com.github.mfamador.callcost

import com.github.mfamador.callcost.exception.InvalidInputException
import com.github.mfamador.callcost.extension.sumByLong
import com.github.mfamador.callcost.model.CallRecord
import java.io.File
import java.time.Duration
import java.time.LocalTime.parse

object CostCalculator {
    fun costInEuros(callLogFile: String) = costInCents(File(callLogFile).useLines { it.toList() }).toDouble() / 100

    fun costInCents(callLog: List<String>): Long {
        val recordsByCaller = callLog.map { parseRecord(it) }.groupBy { it.from }

        val durationByCaller = recordsByCaller.map { it.key to it.value.sumByLong { it.duration } }.toMap()
        val maxDuration = durationByCaller.maxBy { it.value }?.value
        val excludedCallers = durationByCaller.filter { it.value == maxDuration }.map { it.key }

        return recordsByCaller.filter { !excludedCallers.contains(it.key) }.values.flatten()
                .map { it.cost }.sum()
    }

    fun parseRecord(callLog: String): CallRecord {
        val elements = callLog.trim().split(";")
        if (elements.size != 4) throw InvalidInputException("Invalid input file")
        return CallRecord(duration(elements[0], elements[1]).seconds, elements[2], elements[3])
    }

    private fun duration(start: String, end: String): Duration {
        val duration = Duration.between(parse(start), parse(end))
        return if (duration.isNegative)
            duration.plusDays(1)
        else
            duration
    }

}
