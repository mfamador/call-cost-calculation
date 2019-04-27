package com.github.mfamador.callcost

import com.github.mfamador.callcost.calculation.CostCalculator

fun main(args: Array<String>) {

    if (args.size != 1)
        println("usage: ./gradlew run --args='log-file-path'")
    else
        println("${CostCalculator.calculate(args[0])}")
}

