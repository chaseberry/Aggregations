package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.stages.Stage

class Pipeline(val stages: List<Stage>) {

    constructor(vararg stages: Stage) : this(stages.toList())

}