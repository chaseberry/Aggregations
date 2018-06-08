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

    fun writeValue(value: Any?, depth: Int) {//TODO if String quote
        val v = settings.valueMod?.invoke(value) ?: value
        interalBuilder += when (v) {
            is String -> quote(v)
            is Map<*, *> -> return writeMap(v, depth)
            else -> v
        }
    }

    fun writeMap(map: Map<*, *>, depth: Int) {
        with(settings) {

            interalBuilder += documentHeader

            nl(depth + 1)

            val fields = map.toList()

            for (z in fields.indices) {

                writeKey(fields[z].first.toString())

                interalBuilder += keyValueSeperator

                writeValue(fields[z].second, depth)

                if (z != fields.lastIndex || settings.trailingCommas) {
                    interalBuilder += ","
                }

                if (z != fields.lastIndex) {
                    nl(depth + 1)
                }

            }

            nl(depth)
            interalBuilder += documentCloser
        }
    }

    fun writeList(lst: List<*>, depth: Int) {

    }

    override fun toString(): String {
        return interalBuilder.toString()
    }

}