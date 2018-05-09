package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.doc
import edu.csh.chase.aggregations.plusAssign
import org.bson.Document

class Match(val document: Document) : Stage() {

    constructor(vararg pairs: Pair<String, Any?>) : this(doc(*pairs))

}