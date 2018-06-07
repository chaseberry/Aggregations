package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.aggregation.Aggregation
import edu.csh.chase.aggregations.stages.Unwind

fun main(args: Array<String>) {

    val aggregation = Aggregation(
        collection = "test",
        pipeline = Pipeline(
            stages = listOf(
                Unwind(
                    path = "\$test"
                )
            )
        )
    )

    val shellSettings = RenderSettings(
        documentHeader = "{",
        documentCloser = "}",
        keyValueSeperator = ":",
        listHeader = "[",
        listCloser = "]",
        trailingCommas = true,
        quoteKeys = false,
        indentationSize = 2
    )

    //println(AggregationRender(aggregation, shellSettings).render())

}