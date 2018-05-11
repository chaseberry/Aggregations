package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.PipelineParsingException
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
            "\$project" -> parseProjectStage()
            "\$lookup" -> parseLookupStage()
            "\$unwind" -> parseUnwindStage()
            "\$group" -> parseGroupStage()
            "\$count" -> parseCountStage()
            else -> throw StageParsingException.UnknownStage(stageName)
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

    private fun parseUnwindStage(): Unwind = with(getMap("\$unwind")) {
        try {
            Unwind(
                path = string("path"),
                includeArrayIndex = opString("includeArrayIndex"),
                preserveNullAndEmptyArrays = opBoolean("preserveNullAndEmptyArrays")
            )
        } catch (e: ValueException) {
            throw error("\$unwind", e)
        }
    }

    private fun parseCountStage(): Count = Count(input.string("\$count"))

    private fun parseGroupStage(): Group = with(getMap("\$group")) {
        try {
            Group(
                _id = get("_id"),
                fields = Document(this - "_id")
            )
        } catch (e: ValueException) {
            throw error("\$group", e)
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
                let = map.opDoc("let"),
                pipeline = PipelineParser(map.string("from"), map.list("pipeline")).parse(),
                `as` = map.string("as")
            )
        } catch (e: ValueException) {
            throw error("\$lookup", e)
        } catch (e: PipelineParsingException) {
            throw StageParsingException.Subpipeline(e, "\$lookup")
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
            throw error("\$lookup", e)
        }
    }

    private fun getMap(stage: String): Map<String, Any?> {
        val doc = input[stage]

        return when (doc) {
            null -> throw StageParsingException.MissingRequiredValue(stage, stage)
            is Document -> doc
            is Map<*, *> -> doc.mapKeys { it.key.toString() }
            else -> throw StageParsingException.WrongType(stage, "", doc, stage)
        }
    }

    private fun error(stage: String, e: ValueException): StageParsingException {
        return if (e.got == null) {
            StageParsingException.MissingRequiredValue(e.field, stage)
        } else {
            StageParsingException.WrongType(e.field, e.expected, e.got, stage)
        }
    }

}