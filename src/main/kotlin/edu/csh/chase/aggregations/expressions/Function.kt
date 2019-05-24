package edu.csh.chase.aggregations.expressions

import edu.csh.chase.aggregations.schema.Type

abstract class Function(val arguments: List<Type?>, val returnType: Type?) {
}