package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.RenderSettings

class Out(val collection: String) : Stage("\$out") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        return collection
    }

    override fun internalBson(): Any? {
        return collection
    }

}