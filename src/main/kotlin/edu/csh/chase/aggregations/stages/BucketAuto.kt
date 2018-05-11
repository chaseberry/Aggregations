package edu.csh.chase.aggregations.stages

import org.bson.Document

class BucketAuto(val groupBy: Any?,
                 val buckets: Int,
                 val output: Document?,
                 val granularity: String?):Stage() {
}