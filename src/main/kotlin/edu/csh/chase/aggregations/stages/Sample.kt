package edu.csh.chase.aggregations.stages

class Sample(val size: Int) : Stage("\$sample") {

    override fun internalBson(): Any? {
        return size
    }

}