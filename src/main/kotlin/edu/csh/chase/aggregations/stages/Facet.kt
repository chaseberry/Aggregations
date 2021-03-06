package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.Pipeline
import edu.csh.chase.aggregations.RenderSettings
import edu.csh.chase.aggregations.utils.doc

class Facet(val facets: Map<String, Pipeline>) : Stage("\$facet") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeMap(facets, depth)

        return s.toString()
    }

    override fun internalBson(): Any? {
        return doc(
            *facets.map {
                it.key to it.value.toBson()
            }.toTypedArray()
        )
    }

}