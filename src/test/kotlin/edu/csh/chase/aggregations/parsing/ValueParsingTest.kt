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

}