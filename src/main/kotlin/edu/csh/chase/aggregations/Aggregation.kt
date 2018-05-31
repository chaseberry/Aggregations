package edu.csh.chase.aggregations

import org.bson.Document

class Aggregation(val collection: String, val pipeline: Pipeline, val explain: Boolean?, val allowDiskUse: Boolean?,
                  val cursor: Document?,/*TODO cursorObject*/ val maxTimeMS: Int?, val bypassDocumentValidation: Boolean?,
                  val readConcern: Document?, val collation: Document?, val hint: AggregationHint, val comment: String?) {


}