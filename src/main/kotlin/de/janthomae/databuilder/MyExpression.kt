package de.janthomae.databuilder

import com.google.gson.JsonElement

public interface MyExpression<T> {

    fun toElement(): JsonElement

    fun computeValue(): T

    fun materialize(): MyExpression<T>

    fun isNil(): Boolean = false

    fun asMyObject(): MyObject {
        if (this is MyObject) {
            return this
        }
        throw IllegalStateException("This is not a MyObject.")
    }
}