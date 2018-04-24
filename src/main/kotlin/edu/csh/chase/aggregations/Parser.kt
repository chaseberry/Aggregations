package edu.csh.chase.aggregations

class Parser(val input: String) {

    private var index = 0

    private var lineCount = 0

    /**
     * Parsers [ ({}(,)?)+ ]
     */
    fun parse() {
        if (getNextChar() != '[') {
            except("Pipeline must start with '['")
        }

        while (true) {
            if (getNextChar(false) != '{') {//Check start of stage, Don't comsume though, since parseStage will consume
                break
            }

            parseObject()

            if (getNextChar(false) != ',') { //Check if its a comma
                getNextChar() //It is, consume the comma and break since we cannot have more stages
                break
            }
        }

        if (getNextChar() != ']') {
            except("Pipeline must end with ']'")
        }
    }

    /**
     * Parses {}
     */
    private fun parseObject() {
        if (getNextChar() != '{') {
            except("Stage must start with '{'")
        }

        val pairs = ArrayList<Pair<String, Any?>>()

        while (true) {

            if (getNextChar(false) == '}') {
                //Object Done, consume and return
                getNextChar()
                return
            }

            val key = getKey()

            if (getNextChar() != ':') {
                throw except("Must have a `:` after a key")
            }

            val value = getValue()

            pairs.add(key to value)

            if (getNextChar() != ',') {
                throw except("Need a comma after a key/value pair")
            }//TODO dont require trailing comma

        }

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
            str.append(first)
        }

        while (true) {
            val c = getNext(false) ?: throw except("Unexpected End of String")

            if (!quoted && c == ':') { //The key is not quoted and we hit a colon
                return str.toString()  //Return without consuming the ':'
            }

            getNext()//The char wasn't a ':' so consume it

            if (c == '\n') {
                throw except("Keys cannot contain newline")
            }

            if (quoted && c == '"') {//String is quoted and we hit closing '"' return what we got
                return str.toString()
            }

            if (quoted && c == '\\') { //Hit a '\' Need to get the next char and figure out what to do
                str.append(readEscaped())
            } else {
                str.append(c)
            }
        }
    }

    private fun getValue(): Any? {

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

        } while (true)
    }

    private fun getNext(consume: Boolean = true): Char? {
        return input[index].also {
            if (consume) {
                index += 1
            }
        }
    }

    private fun Char.isControl() = this == '{' || this == '}' || this == '[' || this == ']' || this == ':'

    @Throws
    private fun except(msg: String): Exception {
        return RuntimeException()
    }

}