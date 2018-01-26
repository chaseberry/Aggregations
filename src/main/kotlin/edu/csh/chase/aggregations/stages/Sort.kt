package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.doc
import org.bson.Document

class Sort(val document: Document) {

    constructor(vararg pairs: Pair<String, Int>) : this(doc(*pairs))

}