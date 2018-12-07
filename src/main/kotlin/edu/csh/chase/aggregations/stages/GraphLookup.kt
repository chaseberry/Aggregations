package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import org.bson.Document

class GraphLookup(val from: String,
                  val startWith: Any?,
                  val connectToField: String,
                  val connectFromField: String,
                  val maxDepth: Int?,
                  val depthField: String?,
                  val restrictSearchWithMatch: Document?,
                  val `as`: String) : Stage("\$graphLookup") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeMap(
            map = mapOf(
                "from" to from,
                "startWith" to startWith,
                "connectToField" to connectToField,
                "connectFromField" to connectFromField,
                "maxDepth" to maxDepth,
                "depthField" to depthField,
                "restrictSearchWithMatch" to restrictSearchWithMatch,
                "as" to `as`
            ),
            depth = depth
        )

        return s.toString()
    }

}