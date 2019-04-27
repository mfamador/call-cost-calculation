package com.github.mfamador.callcost

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CallCostCalculationTest {

    @Test
    fun `Test total cost from a day log file`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_1.txt").file

        val totalCost = CostCalculator.calculate(file)

        assertThat(totalCost).isEqualTo(30.6)
    }

    @Test
    fun `A call with 4 min and 1 sec should be charged as 5 min`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_2.txt").file

        val totalCost = CostCalculator.calculate(file)

        assertThat(totalCost).isEqualTo(15.0)
    }

    @Test
    fun `All the callers with the longest call are not charged`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_3.txt").file

        val totalCost = CostCalculator.calculate(file)

        assertThat(totalCost).isEqualTo(3.0)
    }

    @Test
    fun `The longest call is not necessarily the most expensive`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_4.txt").file

        val totalCost = CostCalculator.calculate(file)

        assertThat(totalCost).isEqualTo(42.0)
    }
}
