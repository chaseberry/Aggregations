package edu.csh.chase.aggregations

import org.bson.Document

fun doc(vararg pairs: Pair<String, Any?>): Document = Document(mapOf(*pairs))

operator fun StringBuilder.plusAssign(a: Any?) {
    this.append(a)
}

fun <T> List<Pair<String, T>>.toMap() = this.associate { it }

fun quote(string: String): String {
    if (string.isEmpty()) {
        return "\"\""
    }

    val w = StringBuilder()

    w += '"'

    for (z in string.indices) {
        val c = string[z]//current
        w += when (c) {
            '\\' -> "\\\\"
            '"' -> "\\\""
            '\b' -> "\\b"
            '\t' -> "\\t"
            '\n' -> "\\n"
            '\u000C' -> "\\f"
            '\r' -> "\\r"
            else -> c
        }
    }

    w += '"'

    return w.toString()
}
