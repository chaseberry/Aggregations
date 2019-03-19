package edu.csh.chase.aggregations.stages

class Skip(val skip: Int) : Stage("\$skip") {

    override fun internalBson(): Any? {
        return skip
    }
}