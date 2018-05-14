package edu.csh.chase.aggregations.parsing

import edu.csh.chase.aggregations.parsers.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ValueParsingTest {

    private fun String.parse(): Any? = Parser(this).parse()

    @Test
    fun parseNull() {
        assertEquals(null, "null".parse())
    }

    @Test
    fun parseBooleans() {
        assertEquals(true, "true".parse())
        assertEquals(false, "false".parse())
    }

    @Test
    fun parseInt() {
        assertEquals(0, "0".parse())
        assertEquals(14, "14".parse())
        assertEquals(173, "+173".parse())
        assertEquals(-12, "-12".parse())
    }

    @Test
    fun parseDouble() {
        assertEquals(0.0, "0.".parse())
        assertEquals(0.0, ".0".parse())
        assertEquals(14.134, "14.134".parse())
        assertEquals(173.99, "+173.99".parse())
        assertEquals(-1.2, "-1.2".parse())
        assertEquals(1000.0, "1E3".parse())
    }

    @Test
    fun parseString() {
        assertEquals("", "\"\"".parse())
        assertEquals("\"", "\"\\\"\"".parse())
        assertEquals("Hello World!", "\"Hello World!\"".parse())
        assertEquals("\n\r\t", "\"\\n\\r\\t\"".parse())
    }

}