package edu.csh.chase.aggregations.aggregation

import edu.csh.chase.aggregations.Pipeline
import org.bson.Document

class Aggregation(val collection: String,
                  val pipeline: Pipeline,
                  val explain: Boolean? = null,
                  val allowDiskUse: Boolean? = null,
                  val cursor: AggregationCursor? = null,
                  val maxTimeMS: Int? = null,
                  val bypassDocumentValidation: Boolean? = null,
                  val readConcern: Document? = null,
                  val collation: Document? = null,
                  val hint: AggregationHint? = null,
                  val comment: String? = null) {



}