package edu.csh.chase.aggregations.stages

class Unwind(val path: String, val includeArrayIndex: String?, val preserveNullAndEmptyArrays: Boolean?) : Stage() {
}