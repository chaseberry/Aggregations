package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.utils.doc
import org.bson.Document

class Project(val document: Document) : Stage("\$project") {

    constructor(vararg pairs: Pair<String, Any?>) : this(doc(*pairs))

    constructor(map: Map<String, Any?>) : this(Document(map))
}