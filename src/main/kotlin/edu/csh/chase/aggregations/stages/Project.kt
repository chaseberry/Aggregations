package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.doc
import org.bson.Document

class Project(val document: Document) : Stage() {

    constructor(vararg pairs: Pair<String, Any?>) : this(doc(*pairs))

    constructor(map: Map<String, Any?>) : this(Document(map))
}