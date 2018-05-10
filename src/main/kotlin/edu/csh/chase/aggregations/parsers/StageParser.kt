package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.StageParsingException
import edu.csh.chase.aggregations.exceptions.ValueException
import edu.csh.chase.aggregations.stages.*
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
        val map = getMap("\$match")

        //TODO extra validation on stuff
        return Match(map)
    }

    private fun parseProjectStage(): Project {
        val map = getMap("\$project")

        //TODO extra validation on stuff
        return Project(map)
    }

    private fun parseUnwind(): Unwind {
        val map = getMap("\$unwind")

        return try {
            Unwind(
                path = map.string("path"),
                includeArrayIndex = map.opString("includeArrayIndex"),
                preserveNullAndEmptyArrays = map.opBoolean("preserveNullAndEmptyArrays") ?: false
            )
        } catch (e: ValueException) {
            throw error()
        }
    }

    private fun parseLookupStage(): Stage {
        val map = getMap("\$lookup")

        return if ("pipeline" in map) {
            parsePipelineLookup(map)
        } else {
            parseBasicLookup(map)
        }
    }

    private fun parsePipelineLookup(map: Map<String, Any?>): PipelineLookup {
        return try {
            PipelineLookup(
                let =,
                pipeline =,
                `as` = map.string("as")
            )
        } catch (e: ValueException) {
            throw error()
        }
    }

    private fun parseBasicLookup(map: Map<String, Any?>): BasicLookup {
        return try {
            BasicLookup(
                from = map.string("from"),
                localField = map.string("localField"),
                foreignField = map.string("foreignField"),
                `as` = map.string("as")
            )
        } catch (e: ValueException) {
            throw error()
        }
    }

    private fun getMap(stage: String): Map<String, Any?> {
        val doc = input[stage]

        return when (doc) {
            null -> throw error("Value of a stage stage cannot be null")
            is Document -> doc
            is Map<*, *> -> doc.mapKeys { it.key.toString() }
            else -> throw error("Value of a stage stage must be an Object")
        }
    }

    private fun error(msg: String): StageParsingException {
        return StageParsingException()
    }

}