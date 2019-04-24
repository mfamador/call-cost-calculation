package com.github.mfamador.callcost.calculation

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*
import java.io.File

class CallCostCalculationTest {

    @Test
    fun testCalculation() {

        val fileContent = CallCostCalculationTest::class.java.getResource("/samples/call_log_example.txt")

        File(fileContent.file).forEachLine {
            println(it)
        }

        assertThat(1).isEqualTo(1)
    }
}
