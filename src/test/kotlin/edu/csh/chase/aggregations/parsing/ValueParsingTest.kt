package edu.csh.chase.aggregations.parsing

import edu.csh.chase.aggregations.exceptions.ParserException
import edu.csh.chase.aggregations.parsers.Parser
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*


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
        assertEquals("    ", "\"    \"".parse())
        assertEquals("\"", "\"\\\"\"".parse())
        assertEquals("Hello World!", "\"Hello World!\"".parse())
        assertEquals("\n\r\t", "\"\\n\\r\\t\"".parse())
    }

    @Test
    fun parseObjectId() {
        assertEquals(ObjectId("5288ebc1859d268b3ef76e09"), "ObjectId(\"5288ebc1859d268b3ef76e09\")".parse())

        assertNotNull("ObjectId()".parse())

        try {
            "ObjectId(\"123\")".parse()
            fail<ObjectId>("Object Id parsed when it should've failed")
        } catch (e: ParserException) {

        }
    }

    @Test
    fun parseDate() {
        val date = Calendar.getInstance().apply {
            set(2018, 0, 8, 0, 0, 0)
            timeZone = TimeZone.getTimeZone("GMT")
            set(Calendar.MILLISECOND, 0)
        }.time

        assertEquals(date, "ISODate(\"2018-01-08T00:00:00Z\")".parse())

        val date2 = Calendar.getInstance().apply {
            set(2017, 11, 4, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        assertEquals(date2, "ISODate(\"2017-12-04T00:00:00\")".parse())

        assertNotNull("ISODate()".parse())
    }

}