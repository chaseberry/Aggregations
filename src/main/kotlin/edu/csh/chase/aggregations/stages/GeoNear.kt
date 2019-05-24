package edu.csh.chase.aggregations.stages

import edu.csh.chase.aggregations.AggregationsStringBuilder
import edu.csh.chase.aggregations.RenderSettings
import edu.csh.chase.aggregations.utils.docNotNull
import edu.csh.chase.aggregations.utils.removeNulls
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
              val spherical: Boolean,
              val uniqueDocs: Boolean?) : Stage("\$geoNear") {

    override fun renderSelf(depth: Int, settings: RenderSettings): String {
        val s = AggregationsStringBuilder(settings)

        s.writeMap(
            map = mapOf(
                "distanceField" to distanceField,
                "distanceMultiplier" to distanceMultiplier,
                "includeLocs" to includeLocs,
                "limit" to limit,
                "maxDistance" to maxDistance,
                "minDistance" to minDistance,
                "near" to near,
                "num" to num,
                "query" to query,
                "spherical" to spherical,
                "uniqueDocs" to uniqueDocs
            ).removeNulls(settings),
            depth = depth
        )

        return s.toString()
    }

    override fun internalBson(): Any? {
        return docNotNull(
            "distanceField" to distanceField,
            "distanceMultiplier" to distanceMultiplier,
            "includeLocs" to includeLocs,
            "limit" to limit,
            "maxDistance" to maxDistance,
            "minDistance" to minDistance,
            "near" to near,
            "num" to num,
            "query" to query,
            "spherical" to spherical,
            "uniqueDocs" to uniqueDocs
        )
    }

}