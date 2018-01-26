package edu.csh.chase.aggregations

import org.bson.Document

fun doc(vararg pairs: Pair<String, Any?>): Document = Document(mapOf(*pairs))