package edu.csh.chase.aggregations

fun main(args: Array<String>) {

    println(Parser("{key:1,\"key2\":2,       key3  : [   1,      4]}").parse())

}