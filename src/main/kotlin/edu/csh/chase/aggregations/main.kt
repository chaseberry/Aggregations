package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.aggregation.Aggregation
import edu.csh.chase.aggregations.stages.Match
import edu.csh.chase.aggregations.stages.Unwind
import edu.csh.chase.aggregations.utils.doc

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
        keyValueSeperator = " : ",
        listHeader = "[",
        listCloser = "]",
        trailingCommas = true,
        quoteKeys = false,
        indentationSize = 2
    )

    val match = Match(
        "key" to "value",
        "key2" to doc("\$gte" to 10),
        "list" to listOf(1,2,3,4,5,"apple pie")
    )

    println(match.render(0, shellSettings))

    //println(AggregationRender(aggregation, shellSettings).render())

}