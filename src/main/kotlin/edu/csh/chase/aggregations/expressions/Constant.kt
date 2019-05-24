package edu.csh.chase.aggregations.expressions

/**
 * Used for things like
 * $project: {
 *  _id: 1
 * }
 */
class Constant(val constant: Any?) : Expression()