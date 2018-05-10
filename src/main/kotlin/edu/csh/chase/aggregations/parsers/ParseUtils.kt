package edu.csh.chase.aggregations.parsers

import edu.csh.chase.aggregations.exceptions.ValueException
import org.bson.Document
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

fun <K, V> Map<K, V>.nonNull(key: K) = get(key) ?: throw ValueException(key.toString(), "not-null", null)


fun <K, V> Map<K, V>.boolean(key: K): Boolean = key.toString() to nonNull(key) assertType Boolean::class
fun <K, V> Map<K, V>.opBoolean(key: K): Boolean? = get(key)?.let {
    key.toString() to it assertType Boolean::class
}


fun <K, V> Map<K, V>.string(key: K): String = key.toString() to nonNull(key) assertType String::class
fun <K, V> Map<K, V>.opString(key: K): String? = get(key)?.let {
    key.toString() to it assertType String::class
}


fun <K, V> Map<K, V>.doc(key: K): Document = (key.toString() to nonNull(key)).let {
    val m = it.second
    when (m) {
        is Map<*, *> -> Document(m.mapKeys { it.key.toString() })
        is Document -> m
        else -> throw ValueException(it.first, "object", m)
    }
}

fun <K, V> Map<K, V>.opDoc(key: K): Document? = get(key)?.let {
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