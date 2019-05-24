package edu.csh.chase.aggregations.mongo

import com.mongodb.client.MongoDatabase
import edu.csh.chase.aggregations.aggregation.Aggregation

class AggregationRunner(val aggregation: Aggregation, val db: MongoDatabase) {

    fun run(): AggregationResult {

        db.getCollection(aggregation.collection).aggregate(aggregation.pipeline.toBson())
        return AggregationResult()
    }

}