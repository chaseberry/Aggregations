package edu.csh.chase.aggregations

class RenderSettings(val trailingCommas: Boolean,
                     val listHeader: String,
                     val listCloser: String,
                     val documentHeader: String,
                     val documentCloser: String,
                     val keyValueSeperator: String,
                     val indentationSize: Int,
                     val quoteKeys: Boolean,
                     val keyMod: ((String) -> String)?,
                     val valueMod: ((Any?) -> Any?)?) {
}