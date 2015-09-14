
package de.janthomae.databuilder

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

open class MyInt : MyExpressionBase<Int> {

    constructor(value: Int) : super(value)
    constructor(function: () -> Int) : super(function)

    override fun toElement(): JsonElement {
        return JsonPrimitive(computeValue())
    }

    override fun materialize(): MyInt = MyInt(computeValue())
}