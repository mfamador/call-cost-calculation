package com.github.mfamador.callcost

import com.github.mfamador.callcost.CostCalculator.costInCents
import com.github.mfamador.callcost.CostCalculator.costInEuros
import com.github.mfamador.callcost.CostCalculator.parseRecord
import com.github.mfamador.callcost.exception.InvalidInputException
import com.github.mfamador.callcost.model.CallRecord
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import java.time.format.DateTimeParseException

class CallCostCalculationTest {

    @Test
    fun `Test total cost from a log file`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_example.txt").file
        val totalCost = costInEuros(file)
        assertThat(totalCost).isEqualTo(0.51)
    }

    @Test
    fun `Test total cost from an empty log file`() {
        val file = CallCostCalculationTest::class.java.getResource("/call_log_empty.txt").file
        val totalCost = costInEuros(file)
        assertThat(totalCost).isEqualTo(0.0)
    }

    @Test
    fun `handle records ending on next day`() {
        val call = parseRecord("23:59:00;00:01:00;+351914374373;+351215355312")
        assertThat(call.duration).isEqualTo(120)
        assertThat(call.cost).isEqualTo(10)
    }

    @Test
    fun `5 min calls have 25c cost`() {
        val call = CallRecord(300, "+351111111111", "+351222222222" )
        assertThat(call.cost).isEqualTo(25)
    }

    @Test
    fun `5 min and 1 sec calls have 27c cost`() {
        val call = CallRecord(301, "+351111111111", "+351222222222" )
        assertThat(call.cost).isEqualTo(27)
    }

    @Test
    fun `A call with 4 min and 1 sec is charged as 5 min`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:00;09:14:01;+351217538222;+351214434422")
        val totalCost = costInCents(recordList)
        assertThat(totalCost).isEqualTo(25)
    }

    @Test
    fun `All the callers with the longest call are not charged`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:30;09:15:10;+351217538222;+351215355312",
                "09:10:00;09:11:00;+351217538111;+351214434422")
        val totalCost = costInCents(recordList)
        assertThat(totalCost).isEqualTo(5)
    }

    @Test
    fun `The longest call is not necessarily the most expensive`() {
        val recordList = listOf(
                "09:10:00;09:25:00;+351914374373;+351215355312",
                "09:10:00;09:15:00;+351217538222;+351215355312",
                "09:15:00;09:20:00;+351217538222;+351215355312",
                "09:20:00;09:24:00;+351217538222;+351215355312")
        val totalCost = costInCents(recordList)
        assertThat(totalCost).isEqualTo(70)
    }

    @Test
    fun `If all callers have the longest calls the total cost is zero`() {
        val recordList = listOf(
                "09:10:30;09:15:10;+351914374373;+351215355312",
                "09:10:30;09:15:10;+351217538222;+351215355312")
        val totalCost = costInCents(recordList)
        assertThat(totalCost).isEqualTo(0)
    }

    @Test
    fun `Test invalid datetime in record`() {
        val invalidRecordList = listOf("0 9:10:30;09:15:10;+351217538222;+351215355312")
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy {
            costInCents(invalidRecordList)
        }
    }

    @Test
    fun `Test invalid record log`() {
        val invalidRecordList = listOf("09:10:30;09:15:10;+351217538222")
        assertThatExceptionOfType(InvalidInputException::class.java).isThrownBy {
            costInCents(invalidRecordList)
        }
    }
}
