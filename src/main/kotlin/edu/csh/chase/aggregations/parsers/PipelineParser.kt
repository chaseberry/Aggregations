package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.Pipeline
import edu.csh.chase.aggregations.exceptions.PipelineParsingException
import edu.csh.chase.aggregations.exceptions.StageParsingException
import edu.csh.chase.aggregations.exceptions.ValueException
import org.bson.Document

class PipelineParser(val collection: String, val input: List<*>) {

    fun parse(): Pipeline {

        val issues = ArrayList<PipelineParsingException.Issue>()

        val results = input.mapIndexedNotNull { index, it ->
            when (it) {
                is Document -> index to it
                is Map<*, *> -> index to Document(it.mapKeys { it.key.toString() })
                else -> {
                    issues.add(PipelineParsingException.Issue(index + 1, ValueException("", "object", it)))
                    null
                }
            }
        }.mapNotNull {
            try {
                StageParser(it.second).parse()
            } catch (e: StageParsingException) {
                issues.add(PipelineParsingException.Issue(it.first + 1, e))
                null
            }
        }

        if (issues.isNotEmpty()) {
            throw PipelineParsingException(issues)
        }

        return Pipeline(collection, results)
    }


}