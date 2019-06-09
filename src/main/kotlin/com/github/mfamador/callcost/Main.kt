package com.github.mfamador.callcost

import com.github.mfamador.callcost.CostCalculator.costInEuros

fun main(args: Array<String>) = if (args.size != 1) {
    println("usage: calculate-cost.sh input_file")
    val test = TestToDelete("test ")
    println("test = $test")
    println("test = ${test.getMessageEnhanced()}")
} else {

    println("${costInEuros(args[0])}")
}

