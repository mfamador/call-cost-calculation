package com.github.mfamador.callcost

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CallCostCalculationTest {

    @Test
    fun `Test total cost from a log file`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_example.txt").file

        val totalCost = CostCalculator.calculate(file)

        assertThat(totalCost).isEqualTo(30.6)
    }

    @Test
    fun `A call with 4 min and 1 sec should be charged as 5 min`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "17:44:04;17:49:30;+351914374373;+351963433432",
                "09:10:00;09:14:01;+351217538222;+351214434422")

        val totalCost = CostCalculator.calculate(recordList)

        assertThat(totalCost).isEqualTo(15.0)
    }

    @Test
    fun `All the callers with the longest call are not charged`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:30;09:15:10;+351217538222;+351215355312",
                "09:10:00;09:11:00;+351217538111;+351214434422")

        val totalCost = CostCalculator.calculate(recordList)

        assertThat(totalCost).isEqualTo(3.0)
    }

    @Test
    fun `The longest call is not necessarily the most expensive`() {
        val recordList = listOf(
                "09:10:00;09:25:00;+351914374373;+351215355312",
                "09:10:00;09:15:00;+351217538222;+351215355312",
                "09:15:00;09:20:00;+351217538222;+351215355312",
                "09:20:00;09:24:00;+351217538222;+351215355312")

        val totalCost = CostCalculator.calculate(recordList)

        assertThat(totalCost).isEqualTo(42.0)
    }

    @Test
    fun `If only there's only callers with longest calls the total cost is zero`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:30;09:15:10;+351217538222;+351215355312")

        val totalCost = CostCalculator.calculate(recordList)

        assertThat(totalCost).isEqualTo(0.0)
    }
}
