package edu.csh.chase.aggregations.stages

import org.bson.Document

class GraphLookup(val from: String,
                  val startWith: Any?,
                  val connectToField: String,
                  val connectFromField: String,
                  val maxDepth: Int?,
                  val depthField: String?,
                  val restrictSearchWithMatch: Document?,
                  val `as`: String) : Stage("\$graphLookup") {
}