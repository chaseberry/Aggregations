package edu.csh.chase.aggregations.stages

import org.bson.Document

sealed class Lookup(val from: String,
                    val `as`: String) {

    class Basic(from: String, val localField: String, val foreignField: String, `as`: String) : Lookup(from, `as`)

    class Pipeline(from: String, val let: Document?, val pipeline: List<Document>, `as`: String) : Lookup(from, `as`)
}