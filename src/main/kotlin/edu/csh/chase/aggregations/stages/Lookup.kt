package edu.csh.chase.aggregations.stages

class Lookup(val from: String, val localField: String, val foreignField: String, val `as`: String) : Stage("\$lookup") {
}