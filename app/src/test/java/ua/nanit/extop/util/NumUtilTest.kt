package ua.nanit.extop.util

import org.junit.Test
import org.junit.Assert.*

class NumUtilTest {

    @Test
    fun toRawString() {
        val number1 = 0.0000008
        val number2 = 0.00000256
        val number3 = 0.000012000
        val number4 = 1000.0

        val formatted1 = number1.toRawString()
        val formatted2 = number2.toRawString()
        val formatted3 = number3.toRawString()
        val formatted4 = number4.toRawString()

        assertTrue("Actual: $formatted1", formatted1 == "0.0000008")
        assertTrue("Actual: $formatted2", formatted2 == "0.00000256")
        assertTrue("Actual: $formatted3", formatted3 == "0.000012")
        assertTrue("Actual: $formatted4", formatted4 == "1000")
    }
}