package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import edu.csh.chase.aggregations.utils.doc
import org.bson.conversions.Bson

abstract class Stage(val name: String) {

    protected open fun renderSelf(depth: Int, settings: RenderSettings): String = "${settings.documentHeader}${settings.documentCloser}"

    fun render(settings: RenderSettings, depth: Int = 0): String {
        val s = AggregationsStringBuilder(settings)

        s += settings.documentHeader

        s.nl(depth + 1)

        s.writeKey(name)
        s += "${settings.keyValueSeperator}${renderSelf(depth + 1, settings)}"

        s.nl(depth)
        s += settings.documentCloser

        return s.toString()
    }

    fun toBson(): Bson {
        return doc(
            name to internalBson()
        )
    }

    protected abstract fun internalBson(): Any?
}