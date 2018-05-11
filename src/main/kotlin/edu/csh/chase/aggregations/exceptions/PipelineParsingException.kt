package edu.csh.chase.aggregations.exceptions

class PipelineParsingException(val issues: List<Issue>) : Exception(
    "${issues.size} ${if (issues.size == 1) "issue" else "issues"} occurred. They are [${issues.joinToString(", ") { "Stage ${it.stage}: ${it.error.message}" }}]"
) {

    data class Issue(val stage: Int, val error: Exception)

}