package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings

abstract class Stage(val name: String = "") {

    protected open fun renderSelf(depth: Int, settings: RenderSettings): String = "${settings.documentHeader}${settings.documentCloser}"

    fun render(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.indent(depth)
        s += settings.documentHeader

        s.nl(depth + 1)

        s.writeKey(name)
        s += " ${settings.keyValueSeperator} ${renderSelf(depth + 1, settings)}"

        s.nl(depth)
        s += settings.documentCloser

        return s.toString()
    }
}