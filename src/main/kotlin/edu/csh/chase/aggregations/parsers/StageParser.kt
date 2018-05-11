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

        return try {
            when (stageName) {
                "\$addFields" -> parseAddFieldsStage()
                "\$bucket" -> parseBucketStage()
                "\$bucketAuto" -> parseBucketAutoStage()
                "\$count" -> parseCountStage()
                "\$facet" -> parseFacetStage()
                "\$group" -> parseGroupStage()
                "\$lookup" -> parseLookupStage()
                "\$match" -> parseMatchStage()
                "\$project" -> parseProjectStage()
                "\$unwind" -> parseUnwindStage()
                else -> throw StageParsingException.UnknownStage(stageName)
            }
        } catch (e: ValueException) {
            throw error(stageName, e)
        }
    }

    private fun parseAddFieldsStage() = try {
        AddFields(input.doc("\$addFields"))
    } catch (e: ValueException) {
        throw error("\$addFields", e)
    }

    private fun parseBucketStage() = with(getMap("\$bucket")) {
        Bucket(
            groupBy = string("groupBy"),
            boundaries = list("boundaries"),
            default = get("default"),
            output = opDoc("output")
        )
    }

    private fun parseBucketAutoStage() = with(getMap("\$bucketAuth")) {
        BucketAuto(
            groupBy = get("groupBy"),
            buckets = int("buckets"),
            output = opDoc("output"),
            granularity = opString("granularity")
        )
    }

    private fun parseFacetStage() = with(getMap("\$facet")) {
        try {
            Facet(
                this.mapValues {
                    PipelineParser(list(it.key)).parse()
                }
            )
        } catch (e: PipelineParsingException) {
            throw StageParsingException.Subpipeline(e, "\$facet")
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
        Unwind(
            path = string("path"),
            includeArrayIndex = opString("includeArrayIndex"),
            preserveNullAndEmptyArrays = opBoolean("preserveNullAndEmptyArrays")
        )
    }

    private fun parseCountStage(): Count = Count(input.string("\$count"))

    private fun parseGroupStage(): Group = with(getMap("\$group")) {
        Group(
            _id = get("_id"),
            fields = Document(this - "_id")
        )
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
                from = map.string("from"),
                let = map.opDoc("let"),
                pipeline = PipelineParser(map.list("pipeline")).parse(),
                `as` = map.string("as")
            )
        } catch (e: PipelineParsingException) {
            throw StageParsingException.Subpipeline(e, "\$lookup")
        }
    }

    private fun parseBasicLookup(map: Map<String, Any?>) = Lookup(
        from = map.string("from"),
        localField = map.string("localField"),
        foreignField = map.string("foreignField"),
        `as` = map.string("as")
    )

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