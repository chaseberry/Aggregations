package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.RenderSettings

class Limit(val limit: Int) : Stage("\$limit") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        return limit.toString()
    }

    override fun internalBson(): Any? {
        return limit
    }

}