package edu.csh.chase.aggregations

class Parser(val input: String) {

    private var index = 0

    private var lineCount = 0
    private var linePosition = 0

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
                getNextChar()
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

        val first = getNextChar()//Check if the key is quoted

        var quoted = true

        if (first != '"') {
            quoted = false
            str += first
        }

        while (true) {
            val c = getNextChar(false) ?: throw except("Unexpected End of String")

            if (!quoted && c == ':') { //The key is not quoted and we hit a colon
                return str.toString()  //Return without consuming the ':'
            }

            consume()//The char wasn't a ':' so consume it

            if (c == '\n') {
                throw except("Keys cannot contain newline")
            }

            if (quoted && c == '"') {//String is quoted and we hit closing '"' return what we got
                return str.toString()
            }

            str += if (quoted && c == '\\') { //Hit a '\' Need to get the next char and figure out what to do
                readEscaped()
            } else {
                c
            }
        }
    }

    private fun getValue(): Any? {

        return when (getNextChar(false)) {
            '[' -> return parseList()
            '{' -> return parseObject()
            '"' -> return getString()
            else -> getRaw()
        }

    }

    private fun getString(): String {
        return ""
    }

    //numbers, boolean, null, isodate, objectId
    private fun getRaw(): Any? {
        //Read until an escape char (']','}',',')

        val s = StringBuilder()

        while (true) {
            val char = getNextChar(false)

            if (char == '"') {//The raw value contains a '"' eg ObjectId("")
                s += getString()
                continue
            }

            if (char == ',' || char == ']' || char == '}') {
                break
            }

            s += char

            consume()
        }

        return s.toString()
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
            }
            consume()
        } while (true)
    }

    private fun getNext(consume: Boolean = true): Char? {
        return input[index].also {
            linePosition += 1
            if (it == '\n') {
                lineCount += 1
                linePosition = 0
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
    private fun except(msg: String): Exception {
        return RuntimeException()
    }

}