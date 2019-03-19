package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.stages.Stage
import org.bson.conversions.Bson

class Pipeline(val stages: List<Stage>) {

    constructor(vararg stages: Stage) : this(stages.toList())

    fun toBson(): List<Bson> = stages.map { it.toBson() }

}