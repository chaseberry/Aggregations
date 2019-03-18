package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.ParseException
import edu.csh.chase.aggregations.exceptions.ParserException
import edu.csh.chase.aggregations.utils.plusAssign
import edu.csh.chase.aggregations.utils.toMap
import org.bson.types.ObjectId
import java.text.SimpleDateFormat
import java.util.*


class Parser(val input: String) {

    private var index = 0

    private var lineCount = 1
    private var linePosition = 1

    fun parse(): Any? {
        return getValue()
    }

    private fun parseList(): List<Any?> {
        if (getNextChar() != '[') {
            throw except("Pipeline must start with '['")
        }

        val values = ArrayList<Any?>()

        while (true) {

            if (getNextChar(false) == ']') {
                consume()
                break
            }

            values.add(getValue())

            val next = getNextChar()

            if (next == ']') {
                break
            }

            if (next != ',') { //Check if its a comma
                throw except("Required comma after value")
            }
        }

        return values
    }

    /**
     * Parses {}
     */
    private fun parseObject(): Map<String, Any?> {
        if (getNextChar() != '{') {
            throw except("Stage must start with '{'")
        }

        val pairs = ArrayList<Pair<String, Any?>>()

        while (true) {

            if (getNextChar(false) == '}') {
                //Object Done, consume and return
                consume()
                break
            }

            val key = getKey()

            if (getNextChar() != ':') {
                throw except("Must have a `:` after a key")
            }

            pairs.add(key to getValue())

            val next = getNextChar()

            if (next == '}') {
                //Object done, no trailing comma
                break
            }

            if (next != ',') {
                throw except("Need a comma after a key/value pair")
            }//TODO dont require trailing comma

        }

        return pairs.toMap()
    }

    /**
     * Gets a key, can be quoted or not
     * Leaves the : as the next char to consume
     */
    private fun getKey(): String {
        val str = StringBuilder()

        val first = getNextChar(false)//Check if the key is quoted

        if (first == '"') {
            return getString()
        }

        while (true) {
            val c = getNextChar(false) ?: throw except("Unexpected End of String")

            if (c == ':') { //The key is not quoted and we hit a colon
                return str.toString()  //Return without consuming the ':'
            }

            consume()//The char wasn't a ':' so consume it

            if (c == '\n') {
                throw except("Keys cannot contain newline")
            }

            if (c.isControl()) {
                throw except("Unquoted keys must terminate with a ':'")
            }

            str += c
        }
    }

    private fun getValue(): Any? {
        return when (getNextChar(false)) {
            '[' -> parseList()
            '{' -> parseObject()
            '"' -> getString()
            '.', '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> getNumber()
            else -> getRaw()
        }
    }

    private fun getString(): String {
        val str = StringBuilder()

        val first = getNextChar()//Check if the key is quoted

        if (first != '"') {
            throw except("First char in a String must be '\"'")
        }

        while (true) {
            val c = getNext() ?: throw except("Unexpected End of String")

            if (c == '"') {
                break
            }

            str += if (c == '\\') { //Hit a '\' Need to get the next char and figure out what to do
                readEscaped()
            } else {
                c
            }
        }

        return str.toString()
    }

    private fun getNumber(): Number {
        //Read until an escape char ( ']' , '}' , ',' )

        var hasDecimal = false

        var hasExponent = false

        var first = true

        val s = StringBuilder()

        while (true) {
            val char = getNextChar(false) ?: break

            if (char == ',' || char == ']' || char == '}') {
                break
            }

            if (char == '"' || char == ':') {//Hit the next key (or even consumed it
                throw except("Required comma after value")
            }

            if (first && (char == '-' || char == '+')) {
                first = false

                if (char == '-') {
                    s += '-'
                }

                consume()
                continue
            }

            first = false

            when (char) {
                '.' -> {
                    if (hasDecimal) {
                        throw except("Numbers cannot have two '.'s")
                    }
                    hasDecimal = true
                    s += '.'
                }
                'e', 'E' -> {
                    if (hasExponent) {
                        throw except("Numbers cannot have two 'e's")
                    }
                    hasExponent = true
                    s += 'e'
                }
                else -> s += char.takeIf { it.isDigit() } ?: throw except("Numbers cannot have letters in them")
            }

            consume()
        }

        val built = s.toString()

        return if (hasDecimal || hasExponent) {
            built.toDouble()
        } else {
            try {
                built.toInt()
            } catch (e: NumberFormatException) {
                built.toLong()
            } as Number//Not sure why I need to cast, but I do
        }
    }

    //numbers, boolean, null, isodate, objectId
    private fun getRaw(): Any? {
        //Read until an escape char ( ']' , '}' , ',' )

        val s = StringBuilder()

        while (true) {
            val char = getNextChar(false) ?: break

            if (char == '"') {//The raw value contains a '"' eg ObjectId("")
                s += getString()
                continue
            }

            if (char == ',' || char == ']' || char == '}') {
                break
            }

            if (char == '"' || char == ':') {//Hit the next key (or even consumed it
                throw except("Required comma after value")
            }

            s += char

            consume()
        }

        val v = s.toString()

        Regex("^ObjectId\\((.*)\\)\$").matchEntire(v)?.let {
            return try {
                val id = it.groupValues[1]
                if (id.isEmpty()) {
                    ObjectId()
                } else {
                    ObjectId(id)
                }
            } catch (e: IllegalArgumentException) {
                throw except(e.toString())
            }
        }

        Regex("^ISODate\\((.*)\\)\$").matchEntire(v)?.let {
            val time = it.groupValues[1]
            return if (time.isEmpty()) {
                Date()
            } else {
                SimpleDateFormat().parse(it.groupValues[1])
            }
        }

        Regex("^\\/(.*)\\/\$").matchEntire(v)?.let {
            return Regex(it.groupValues[1])
        }

        return when (v) {
            "null" -> null
            "true" -> true
            "false" -> false
            else -> v
        }
    }

    /**
     * A '\' was hit. Check and consume the next char and return it's parsed value
     */
    private fun readEscaped(): Char {
        val next = getNext()
        return when (next) {
            'n' -> '\n'
            't' -> '\t'
            '\\' -> '\\'
            '"' -> '"'
            'b' -> '\b'
            'r' -> '\r'
            '\'' -> '\''
            else -> throw except("Invalid escape char '\\$next'")
        }
    }

    private fun getNextChar(consume: Boolean = true): Char? {
        do {
            val c = getNext(consume) ?: return null

            if (!c.isWhitespace() && !c.isISOControl()) {
                return c
            } else if (!consume) {
                //no consume, but is whitespace. consume
                consume()
            }

        } while (true)
    }

    private fun getNext(consume: Boolean = true): Char? {
        return input.getOrNull(index).also {
            linePosition += 1

            if (it == '\n') {
                lineCount += 1
                linePosition = 1
            }

            if (consume) {
                index += 1
            }
        }
    }

    private fun consume() {
        index += 1
    }

    private fun Char.isControl() = this == '{' || this == '}' || this == '[' || this == ']' || this == ':'

    @Throws
    private fun except(msg: String): ParseException {
        return ParserException(msg, lineCount, linePosition)
    }

}