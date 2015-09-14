package de.janthomae.databuilder

import com.google.gson.JsonElement


public class MyComputedExpression<T>(val compute: () -> MyExpression<T>) : MyExpression<T> {

    override fun toElement(): JsonElement = compute().toElement()
    override fun computeValue(): T = compute().computeValue()
    override fun materialize(): MyExpression<T> = compute().materialize()

}