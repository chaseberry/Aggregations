package edu.csh.chase.aggregations

import org.bson.Document

fun doc(vararg pairs: Pair<String, Any?>): Document = Document(mapOf(*pairs))

operator fun StringBuilder.plusAssign(a: Any?) {
    this.append(a)
}

fun <T> List<Pair<String, T>>.toMap() = this.associate{it}