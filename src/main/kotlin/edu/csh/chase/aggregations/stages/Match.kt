package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.doc
import org.bson.Document

class Match(val document: Document) {

    constructor(vararg pairs: Pair<String, Any?>) : this(doc(*pairs))

}