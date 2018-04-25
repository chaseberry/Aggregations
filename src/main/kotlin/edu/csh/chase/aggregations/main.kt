package edu.csh.chase.aggregations

import java.io.File

fun main(args: Array<String>) {

    println(Parser(File("/Users/chase/Code/aggregations/src/main/kotlin/edu/csh/chase/aggregations/test.txt").readText()).parse())

}