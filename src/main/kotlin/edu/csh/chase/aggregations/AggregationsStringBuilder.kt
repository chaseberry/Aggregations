package edu.csh.chase.aggregations

import edu.csh.chase.aggregations.stages.Stage
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
            is Stage -> v.render(settings, depth)
            is Pipeline -> return writeList(v.stages, depth)
            is Map<*, *> -> return writeMap(v, depth)
            is List<*> -> return writeList(v, depth)
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

                writeValue(fields[z].second, depth + 1)

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
        with(settings) {

            interalBuilder += listHeader

            nl(depth + 1)

            for (z in lst.indices) {

                writeValue(lst[z], depth + 1)

                if (z != lst.lastIndex || settings.trailingCommas) {
                    interalBuilder += ","
                }

                if (z != lst.lastIndex) {
                    nl(depth + 1)
                }

            }

            nl(depth)
            interalBuilder += listCloser

        }
    }

    override fun toString(): String {
        return interalBuilder.toString()
    }

}