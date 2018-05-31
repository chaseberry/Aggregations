package edu.csh.chase.aggregations.stages

import org.bson.Document

class GeoNear(val distanceField: String,
              val distanceMultiplier: Double?,
              val includeLocs: String?,
              val limit: Int?,
              val maxDistance: Double?,
              val minDistance: Double?,
              val near: Any,
              val num: Int?,
              val query: Document?,
              val uniqueDocs: Boolean?,
              val spherical: Boolean) : Stage("\$geoNear") {
}