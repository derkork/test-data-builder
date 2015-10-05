package de.janthomae.databuilder

import com.google.gson.JsonElement
import com.google.gson.JsonObject

public open class MyObject : MyExpression<Any> {


    var properties = linkedMapOf<String, MyExpression<*>>()

    override fun computeValue(): Any {
        throw UnsupportedOperationException("ComputeValue is undefined for objects.")
    }

    override fun toElement(): JsonElement {
        val result = JsonObject()
        for (i in properties) {
            result.add(i.key, i.value.toElement())
        }
        return result
    }

    public fun <T : MyExpression<*>> prop(name: String, expression: T): T {
        properties[name] = expression
        return expression
    }

    public fun prop(name: String, init: MyObject.() -> Unit): MyObject {
        val obj = prop(name, MyObject())
        obj.init()
        return obj
    }

    public fun prop(name: String, value: String): MyString {
        return prop(name, MyString(value))
    }

    public fun prop(name: String, value: Int): MyInt {
        return prop(name, MyInt(value))
    }

    override fun materialize(): MyExpression<Any> {
        val copy = linkedMapOf<String, MyExpression<*>>()
        for ( i in properties) {
            copy[i.key] = i.value.materialize()
        }
        val result = MyObject()
        result.properties = copy
        return result
    }


}