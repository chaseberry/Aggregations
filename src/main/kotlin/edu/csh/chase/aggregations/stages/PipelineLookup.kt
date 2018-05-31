package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.Pipeline
import org.bson.Document

class PipelineLookup(val from: String,
                     val let: Document?,
                     val pipeline: Pipeline,
                     val `as`: String) : Stage("\$lookup") {
}