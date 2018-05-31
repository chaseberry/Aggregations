package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings

abstract class Stage(val name: String) {

    protected abstract fun renderSelf(depth: Int, settings: RenderSettings): String

    fun render(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.indent(depth)
        s += settings.documentHeader

        s.nl(depth + 1)

        s.writeKey(name)
        s += " ${settings.keyValueSeperator} ${render(depth + 2, settings)}"

        s.nl(depth + 1)
        s += settings.documentCloser

        return s.toString()
    }
}