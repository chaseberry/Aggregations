package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import org.bson.Document

//TODO expression map
class AddFields(val document: Document) : Stage("\$addFields") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeValue(document, depth)

        return s.toString()
    }

    override fun internalBson(): Any? {
        return document
    }

}