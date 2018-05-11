package edu.csh.chase.aggregations.parsing

import edu.csh.chase.aggregations.parsers.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ValueParsingTest {

    @Test
    fun parseNull(){
        assertEquals(null, Parser("null").parse())
    }

}