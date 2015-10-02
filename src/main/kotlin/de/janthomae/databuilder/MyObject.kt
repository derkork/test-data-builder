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

    public fun get(path: String): MyExpression<*> {
        val index = path.indexOf(".")
        if (index != -1) {
            val propertyName = path.substring(0, index)
            val rest = path.substring(index + 1)
            val value = properties[propertyName]
            if ( value is MyObject) {
                return value.get(rest)
            }
            throw IllegalArgumentException("The property at path " + path + " is no object.")
        }
        val result = properties[path]
        if (result != null) {
            return result;
        }
        throw IllegalArgumentException("The property at path " + path + " does not exist.")
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