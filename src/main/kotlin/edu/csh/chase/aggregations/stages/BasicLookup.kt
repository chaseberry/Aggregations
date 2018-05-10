package edu.csh.chase.aggregations.stages

class BasicLookup(val from: String, val localField: String, val foreignField: String, val `as`: String) : Stage() {
}