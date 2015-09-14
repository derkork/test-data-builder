package de.janthomae.databuilder

import com.google.gson.JsonElement

public interface MyExpression<T> {

    fun toElement() : JsonElement

    fun computeValue() : T

    fun materialize() : MyExpression<T>
}