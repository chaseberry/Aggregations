package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import edu.csh.chase.aggregations.utils.doc
import org.bson.Document

class Match(val document: Document) : Stage("\$match") {

    constructor(vararg pairs: Pair<String, Any?>) : this(doc(*pairs))

    constructor(map: Map<String, Any?>) : this(Document(map))

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        with(settings) {
            s += documentHeader
            s.nl(depth + 1)

            val fields = document.toList()

            for (z in fields.indices) {
                s.writeKey(fields[z].first)
                s += keyValueSeperator
                s.writeValue(fields[z].second)

                if (z != fields.lastIndex || settings.trailingCommas) {
                    s += ","
                }

                if (z != fields.lastIndex) {
                    s.nl(depth + 1)
                }

            }

            s.nl(depth)
            s += documentCloser
        }

        return s.toString()
    }

}