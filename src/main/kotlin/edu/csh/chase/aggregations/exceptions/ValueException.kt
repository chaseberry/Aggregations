package edu.csh.chase.aggregations.exceptions

class ValueException(val field: String, val expected: String, val got: Any?) :
    Exception("$field expected ${expected.javaClass.name}. Got ${got?.let { it.javaClass.name }}") {
}