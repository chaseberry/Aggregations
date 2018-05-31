package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.Pipeline

class Facet(val facets: Map<String, Pipeline>) : Stage("\$facet") {
}