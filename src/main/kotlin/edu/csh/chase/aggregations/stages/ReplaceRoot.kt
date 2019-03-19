package edu.csh.chase.aggregations.stages

class ReplaceRoot(val newRoot: Any) : Stage("\$replaceRoot") {

    override fun internalBson(): Any? {
        return newRoot
    }

}