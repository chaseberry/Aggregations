package edu.csh.chase.aggregations

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

    fun writeKey(key: String) {//TODO quote if quoted
        interalBuilder += settings.keyMod?.invoke(key) ?: key
    }

    fun writeValue(value: Any?) {//TODO if String quote
        interalBuilder += settings.valueMod?.invoke(value) ?: value
    }

    override fun toString(): String {
        return interalBuilder.toString()
    }

}