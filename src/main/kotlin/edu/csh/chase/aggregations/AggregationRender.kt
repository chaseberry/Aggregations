package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.aggregation.Aggregation

class AggregationRender(val aggregation: Aggregation, val settings: RenderSettings) {

    fun render(): String {
        val s = AggregationsStringBuilder(settings)

        //TODO header
        s += "db.${aggregation.collection}.aggregate(\n"

        s.indent(1)

        s += settings.listHeader

        s += "\n"

        val stages = aggregation.pipeline.stages.map { it.render(settings, 2) }

        for (z in stages.indices) {
            s += stages[z]

            if (z != stages.lastIndex || settings.trailingCommas) {
                s += ","
            }

            s += "\n"
        }

        s.indent(1)

        s += settings.listCloser

        s += "\n"

        //TODO closer
        s += ");"

        return s.toString()
    }

}