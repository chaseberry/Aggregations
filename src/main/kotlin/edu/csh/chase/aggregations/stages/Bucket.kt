package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.utils.docNotNull
import org.bson.Document

class Bucket(val groupBy: String,
             val boundaries: List<*>,
             val default: Any?,
             val output: Document?) : Stage("\$bucket") {

    override fun internalBson(): Any? {
        return docNotNull(
            "groupBy" to groupBy,
            "boundaries" to boundaries,
            "default" to default,
            "output" to output
        )
    }

}