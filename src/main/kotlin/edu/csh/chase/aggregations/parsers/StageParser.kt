package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.StageParsingException
import edu.csh.chase.aggregations.stages.Match
import edu.csh.chase.aggregations.stages.Stage
import org.bson.Document

class StageParser(val input: Map<String, Any?>) {

    fun parse(): Stage {
        if (input.size != 1) {
            throw error("Stage objects can only have 1 key")
        }

        val stageName = input.entries.first().key

        return when (stageName) {
            "\$match" -> parseMatchStage()
            else -> throw error("Unknown Stage $stageName")
        }
    }

    private fun parseMatchStage(): Match {
        val doc = input["\$match"]

        val map: Map<String, Any?> = when (doc) {
            null -> throw error("Value of a Match stage cannot be null")
            is Document -> doc
            is Map<*, *> -> doc.mapKeys { it.key.toString() }
            else -> throw error("Value of a match stage must be an Object")
        }

        //TODO extra validation on stuff
        return Match(map)
    }

    private fun error(msg: String): StageParsingException {
        return StageParsingException()
    }

}