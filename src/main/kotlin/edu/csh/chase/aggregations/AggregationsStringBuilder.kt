package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.utils.plusAssign
import edu.csh.chase.aggregations.utils.quote

class AggregationsStringBuilder(val settings: RenderSettings) {

    private val interalBuilder = StringBuilder()

    operator fun plusAssign(a: Any?) {
        interalBuilder.append(a)
    }

    fun nl(depth: Int) {
        interalBuilder.append("\n")
        indent(depth)
    }

    fun indent(depth: Int) {
        for (noOp in 1..(settings.indentationSize * depth)) {
            interalBuilder.append(' ')
        }
    }

    fun writeKey(key: String) {
        val key = (settings.keyMod?.invoke(key) ?: key)
        interalBuilder += if (settings.quoteKeys || "." in key) {
            quote(key)
        } else {
            key
        }
    }

    fun writeValue(value: Any?) {//TODO if String quote
        val v = settings.valueMod?.invoke(value) ?: value
        interalBuilder += when (v) {
            is String -> quote(v)
            else -> v
        }
    }

    override fun toString(): String {
        return interalBuilder.toString()
    }

}