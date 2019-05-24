package edu.csh.chase.aggregations.schema

open class Field(val name: String, val type: Type?) {

    class Object(name: String, val fields: List<Field>) : Field(name, MongoType.`object`.type())

    class Array(name: String, val elementType: MongoType?) : Field(name, MongoType.array.type())
    
}