package edu.csh.chase.aggregations.schema

enum class MongoType(val number: Int) {
    double(1),
    string(2),
    `object`(3),
    array(4),
    binData(5),
    objectId(7),
    bool(8),
    date(9),
    `null`(10),
    regex(11),
    javascript(13),
    javascriptWithScope(15),
    int(16),
    timestamp(17),
    long(18),
    decimal(19),
    minKey(-1),
    maxKey(127);

    fun type(): Type = Type(this)
}