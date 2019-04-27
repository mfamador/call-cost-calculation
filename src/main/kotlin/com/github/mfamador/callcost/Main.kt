package com.github.mfamador.callcost

fun main(args: Array<String>) = if (args.size != 1) {
    println("usage: calculate-cost.sh input_file")
} else {
    println("${CostCalculator.calculate(args[0])}")
}

