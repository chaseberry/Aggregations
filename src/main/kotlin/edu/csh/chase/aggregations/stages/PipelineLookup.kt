package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.Pipeline
import edu.csh.chase.aggregations.utils.docNotNull
import org.bson.Document

class PipelineLookup(val from: String,
                     val let: Document?,
                     val pipeline: Pipeline,
                     val `as`: String) : Stage("\$lookup") {

    override fun internalBson(): Any? {
        return docNotNull(
            "from" to from,
            "let" to let,
            "pipeline" to pipeline.toBson(),
            "as" to `as`
        )
    }
}