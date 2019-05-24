package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import edu.csh.chase.aggregations.utils.doc
import org.bson.Document

class Match(val document: Document) : Stage("\$match") {

    constructor(vararg pairs: Pair<String, Any?>) : this(doc(*pairs))

    constructor(map: Map<String, Any?>) : this(Document(map))

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeMap(document, depth)

        return s.toString()
    }

    override fun internalBson(): Any? {
        return document
    }

}