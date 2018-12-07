package edu.csh.chase.aggregations

class RenderSettings(val trailingCommas: Boolean,
                     val listHeader: String,
                     val listCloser: String,
                     val documentHeader: String,
                     val documentCloser: String,
                     val keyValueSeperator: String,
                     val indentationSize: Int,
                     val quoteKeys: Boolean,
                     val includeNullValues: Boolean,
                     val keyMod: ((String) -> String)? = null,
                     val valueMod: ((Any?) -> Any?)? = null) {
}