package edu.csh.chase.aggregations.stages

import org.bson.Document

class AddFields(val doc: Document) : Stage("\$addFields") {
}