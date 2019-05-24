package edu.csh.chase.aggregations.schema

open class Field(val name: String, val type: MongoType?) {

    class Object(name: String, val fields: List<Field>) : Field(name, MongoType.`object`)

    class Array(name: String, val elementType: MongoType?) : Field(name, MongoType.array)
    
}