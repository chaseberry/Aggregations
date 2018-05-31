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

    w.append("\"")
    for (z in string.indices) {
        val c = string[z]//current
        when (c) {
            '\\' -> w.append("\\\\")
            '"' -> w.append("\\\"")
            '\b' -> w.append("\\b")
            '\t' -> w.append("\\t")
            '\n' -> w.append("\\n")
            '\u000C' -> w.append("\\f")
            '\r' -> w.append("\\r")
            else -> {
                if (c < ' ' || (c >= '\u0080' && c < '\u00a0')
                    || (c >= '\u2000' && c < '\u2100')) {
                    w.append("\\u")
                    val hhhh = Integer.toHexString(c.toInt())
                    w.append("0000", 0, 4 - hhhh.length)
                    w.append(hhhh)
                } else {
                    w.append(c.toString())
                }
            }
        }
    }
    w.append("\"")
    return w.toString()
}
