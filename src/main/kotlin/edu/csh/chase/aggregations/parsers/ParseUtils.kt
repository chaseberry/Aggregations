package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.ValueException
import kotlin.reflect.KClass

fun <K, V> Map<K, V>.nonNull(key: K) = get(key) ?: throw ValueException()


fun <K, V> Map<K, V>.boolean(key: K): Boolean = nonNull(key) assertType Boolean::class
fun <K, V> Map<K, V>.opBoolean(key: K): Boolean? = get(key)?.let { it assertType Boolean::class }


fun <K, V> Map<K, V>.string(key: K): String = nonNull(key) assertType String::class
fun <K, V> Map<K, V>.opString(key: K): String? = get(key)?.let { it assertType String::class }

private infix fun <T, R : Any> T.assertType(`class`: KClass<R>): R {
    if (!`class`.isInstance(this)) {
        throw ValueException()
    }
    return this as R
}