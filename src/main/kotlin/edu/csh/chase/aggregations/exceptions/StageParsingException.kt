package edu.csh.chase.aggregations.exceptions

sealed class StageParsingException(val stage: String, msg: String) : ParseException(msg) {
    class UnknownStage(stage: String) : StageParsingException(stage, "Unknown stage $stage")
    class MissingRequiredValue(val field: String, stage: String) : StageParsingException(stage, "$stage requires field $field")
    class WrongType(val field: String, val expected: String, val got: Any?, stage: String)
        : StageParsingException(stage, "$stage.$field expected $expected. Got $got")

    class Subpipeline(val error: PipelineParsingException, stage: String)
        : StageParsingException(stage, "Error parsing $stage: ${error.message}")
}