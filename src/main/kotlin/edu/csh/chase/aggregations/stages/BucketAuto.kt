package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.utils.docNotNull
import org.bson.Document

class BucketAuto(val groupBy: Any?, //TODO expression type
                 val buckets: Int,
                 val output: Document?,
                 val granularity: String?) : Stage("\$bucketAuto") {

    override fun internalBson(): Any? {
        return docNotNull(
            "groupBy" to groupBy,
            "buckets" to buckets,
            "output" to output,
            "granularity" to granularity
        )
    }

}