package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings

class Unwind(val path: String,
             val includeArrayIndex: String? = null,
             val preserveNullAndEmptyArrays: Boolean? = null) : Stage("\$unwind") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        val fields = mapOf(
            "path" to path,
            "includeArrayIndex" to includeArrayIndex,
            "preserveNullAndEmptyArrays" to preserveNullAndEmptyArrays
        ).filter { it.value != null }

        s.writeValue(fields, depth)

        return s.toString()
    }

}