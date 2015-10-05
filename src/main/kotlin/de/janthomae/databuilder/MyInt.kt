
package de.janthomae.databuilder

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

open class MyInt : MyExpressionBase<Long> {

    constructor(value: Long) : super(value)
    constructor(value: Int) : super(value.toLong())
    constructor(function: () -> Long) : super(function)

    override fun toElement(): JsonElement {
        return JsonPrimitive(computeValue())
    }

    override fun materialize(): MyInt = MyInt(computeValue())
}