package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.ValueException
import org.bson.Document
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

fun <K, V> Map<K, V?>.nonNull(key: K): V = get(key) ?: throw ValueException(key.toString(), "not-null", null)


fun <K, V> Map<K, V?>.boolean(key: K): Boolean = key.toString() to nonNull(key) assertType Boolean::class
fun <K, V> Map<K, V?>.opBoolean(key: K): Boolean? = get(key)?.let {
    key.toString() to it assertType Boolean::class
}


fun <K, V> Map<K, V?>.int(key: K): Int = key.toString() to nonNull(key) assertType Int::class
fun <K, V> Map<K, V?>.opInt(key: K): Int? = get(key)?.let {
    key.toString() to it assertType Int::class
}


fun <K, V> Map<K, V?>.double(key: K): Double = key.toString() to nonNull(key) assertType Double::class
fun <K, V> Map<K, V?>.opDouble(key: K): Double? = get(key)?.let {
    key.toString() to it assertType Double::class
}


fun <K, V> Map<K, V?>.string(key: K): String = key.toString() to nonNull(key) assertType String::class
fun <K, V> Map<K, V?>.opString(key: K): String? = get(key)?.let {
    key.toString() to it assertType String::class
}


fun <K, V> Map<K, V?>.list(key: K): List<*> = (key.toString() to nonNull(key)).let {
    val m = it.second
    when (m) {
        is List<*> -> m
        is Array<*> -> m.toList()
        else -> throw ValueException(it.first, "list", m)
    }
}

fun <K, V> Map<K, V?>.opList(key: K): List<*>? = get(key)?.let {
    when (it) {
        is List<*> -> it
        is Array<*> -> it.toList()
        else -> throw ValueException(key.toString(), "list", it)
    }
}


fun <K, V> Map<K, V?>.doc(key: K): Document = (key.toString() to nonNull(key)).let {
    val m = it.second
    when (m) {
        is Map<*, *> -> Document(m.mapKeys { it.key.toString() })
        is Document -> m
        else -> throw ValueException(it.first, "object", m)
    }
}

fun <K, V> Map<K, V?>.opDoc(key: K): Document? = get(key)?.let {
    when (it) {
        is Map<*, *> -> Document(it.mapKeys { it.key.toString() })
        is Document -> it
        else -> throw ValueException(key.toString(), "object", it)
    }
}


private infix fun <T, R : Any> Pair<String, T>.assertType(`class`: KClass<R>): R {
    if (!`class`.isInstance(this.second)) {
        throw ValueException(this.first, `class`.jvmName, this.second)
    }
    return this.second as R
}