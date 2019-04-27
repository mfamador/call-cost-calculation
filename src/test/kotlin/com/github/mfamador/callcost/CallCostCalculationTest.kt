package com.github.mfamador.callcost

import com.github.mfamador.callcost.CostCalculator.calculate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CallCostCalculationTest {

    @Test
    fun `Test total cost from a log file`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_example.txt").file

        val totalCost = calculate(file)

        assertThat(totalCost).isEqualTo(0.51)
    }

    @Test
    fun `A call with 4 min and 1 sec is charged as 5 min`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:00;09:14:01;+351217538222;+351214434422")

        val totalCost = calculate(recordList)

        assertThat(totalCost).isEqualTo(0.25)
    }

    @Test
    fun `All the callers with the longest call are not charged`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:30;09:15:10;+351217538222;+351215355312",
                "09:10:00;09:11:00;+351217538111;+351214434422")

        val totalCost = calculate(recordList)

        assertThat(totalCost).isEqualTo(0.05)
    }

    @Test
    fun `The longest call is not necessarily the most expensive`() {
        val recordList = listOf(
                "09:10:00;09:25:00;+351914374373;+351215355312",
                "09:10:00;09:15:00;+351217538222;+351215355312",
                "09:15:00;09:20:00;+351217538222;+351215355312",
                "09:20:00;09:24:00;+351217538222;+351215355312")

        val totalCost = calculate(recordList)

        assertThat(totalCost).isEqualTo(0.7)
    }

    @Test
    fun `If all callers have the longest calls the total cost is zero`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:30;09:15:10;+351217538222;+351215355312")

        val totalCost = calculate(recordList)

        assertThat(totalCost).isEqualTo(0.0)
    }
}
