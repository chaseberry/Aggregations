package edu.csh.chase.aggregations

import org.bson.Document

class Aggregation(val collection: String,
                  val pipeline: Pipeline,
                  val explain: Boolean? = null, 
                  val allowDiskUse: Boolean? = null,
                  val cursor: Document? = null,//TODO cursorObject
                  val maxTimeMS: Int? = null,
                  val bypassDocumentValidation: Boolean? = null,
                  val readConcern: Document? = null,
                  val collation: Document? = null,
                  val hint: AggregationHint? = null,
                  val comment: String? = null) {


}