package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.ValueException
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

fun <K, V> Map<K, V>.nonNull(key: K) = get(key) ?: throw ValueException(key.toString(), "null", null)


fun <K, V> Map<K, V>.boolean(key: K): Boolean = key.toString() to nonNull(key) assertType Boolean::class
fun <K, V> Map<K, V>.opBoolean(key: K): Boolean? = get(key)?.let {
    key.toString() to it assertType Boolean::class
}


fun <K, V> Map<K, V>.string(key: K): String = key.toString() to nonNull(key) assertType String::class
fun <K, V> Map<K, V>.opString(key: K): String? = get(key)?.let {
    key.toString() to it assertType String::class
}

private infix fun <T, R : Any> Pair<String, T>.assertType(`class`: KClass<R>): R {
    if (!`class`.isInstance(this.second)) {
        throw ValueException(this.first, `class`.jvmName, this.second)
    }
    return this.second as R
}