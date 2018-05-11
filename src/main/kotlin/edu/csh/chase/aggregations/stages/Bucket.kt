package edu.csh.chase.aggregations.stages

import org.bson.Document

class Bucket(val groupBy: String,
             val boundaries: List<*>,
             val default: Any?,
             val output: Document?) {
}