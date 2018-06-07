package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.utils.doc
import org.bson.Document

class Sort(val document: Document) : Stage("\$sort") {

    constructor(vararg pairs: Pair<String, Int>) : this(doc(*pairs))

}