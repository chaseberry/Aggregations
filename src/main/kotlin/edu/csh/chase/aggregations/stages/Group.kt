package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import edu.csh.chase.aggregations.exceptions.ValueException
import edu.csh.chase.aggregations.utils.doc
import org.bson.Document

class Group(val _id: Any?, val fields: Document) : Stage("\$group") {

    constructor(vararg fields: Pair<String, Any?>) : this(
        _id = fields.find { it.first == "_id" } ?: throw ValueException("_id", "_id field", ""),
        fields = doc(*(fields)).apply { remove("_id") }
    )

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        fields["_id"] = _id

        s.writeMap(fields, depth)

        return s.toString()
    }

    override fun internalBson(): Any? {
        return doc(
            "_id" to _id
        ).putAll(fields)
    }

}