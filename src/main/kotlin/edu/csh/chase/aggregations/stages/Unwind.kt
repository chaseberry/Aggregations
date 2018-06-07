package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings

class Unwind(val path: String,
             val includeArrayIndex: String? = null,
             val preserveNullAndEmptyArrays: Boolean? = null) : Stage("\$unwind") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s += settings.documentHeader

        s.nl(depth + 1)

        val fields = listOf(
            "path" to path,
            "includeArrayIndex" to includeArrayIndex,
            "preserveNullAndEmptyArrays" to preserveNullAndEmptyArrays
        ).filter { it.second != null }

        for (z in fields.indices) {
            s.writeKey(fields[z].first)
            s += settings.keyValueSeperator
            s.writeValue(fields[z].second)

            if (z != fields.lastIndex || settings.trailingCommas) {
                s += ","
            }

            if (z != fields.lastIndex) {
                s.nl(depth + 1)
            }

        }



        s.nl(depth)
        s += settings.documentCloser

        return s.toString()
    }

}