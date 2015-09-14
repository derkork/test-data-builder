package de.janthomae.databuilder

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

open class MyString : MyExpressionBase<String> {

    constructor(value: String) : super(value)
    constructor(function: () -> String) : super(function)

    override fun toElement(): JsonElement {
        return JsonPrimitive(computeValue())
    }

    override fun materialize(): MyString = MyString(computeValue())
}