package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.RenderSettings

class Count(val field: String) : Stage("\$count") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        return field
    }

    override fun internalBson(): Any? {
        return field
    }

}