package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings

class Lookup(val from: String,
             val localField: String,
             val foreignField: String,
             val `as`: String) : Stage("\$lookup") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeMap(
            depth = depth,
            map = mapOf(
                "from" to from,
                "localField" to localField,
                "foreignField" to foreignField,
                "as" to `as`
            )
        )

        return s.toString()
    }

}