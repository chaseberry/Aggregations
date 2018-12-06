package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.Pipeline
import edu.csh.chase.aggregations.RenderSettings

class Facet(val facets: Map<String, Pipeline>) : Stage("\$facet") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeMap(facets, depth)

        return s.toString()
    }

}