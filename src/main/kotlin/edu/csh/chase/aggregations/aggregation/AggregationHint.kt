package edu.csh.chase.aggregations.aggregation

import org.bson.Document

sealed class AggregationHint {

    class Name(val name: String) : AggregationHint()
    class Doc(val doc: Document) : AggregationHint()

}