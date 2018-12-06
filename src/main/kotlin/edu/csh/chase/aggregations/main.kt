package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.aggregation.Aggregation
import edu.csh.chase.aggregations.stages.*
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
        "list" to listOf(1, 2, 3, 4, 5, "apple pie")
    )

    val facet = Facet(
        facets = mapOf(
            "match" to Pipeline(listOf(match, Limit(5))
            ),
            "idk" to Pipeline(listOf(Limit(5), Count("count")))
        )
    )

    println(facet.render(shellSettings))

    //println(AggregationRender(aggregation, shellSettings).render())

}