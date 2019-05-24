package edu.csh.chase.aggregations.stages

class Redact(val expresion: Any) : Stage("\$redact"){

    override fun internalBson(): Any? {
        return expresion
    }

}