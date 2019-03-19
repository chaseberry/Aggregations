package edu.csh.chase.aggregations.stages

class SortByCount(val expression: Any) : Stage("\$sortByCount") {

    override fun internalBson(): Any? {
        return expression
    }

}