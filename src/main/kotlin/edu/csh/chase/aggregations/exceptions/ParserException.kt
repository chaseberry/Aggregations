package edu.csh.chase.aggregations.exceptions

class ParserException(msg: String, val line: Int, val index: Int) : ParseException("$msg at ($line, $index)") {
}