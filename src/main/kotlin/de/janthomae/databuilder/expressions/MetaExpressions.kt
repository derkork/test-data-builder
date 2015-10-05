package de.janthomae.databuilder.expressions

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import de.janthomae.databuilder.MyComputedExpression
import de.janthomae.databuilder.MyExpression
import de.janthomae.databuilder.MyObject
import de.janthomae.databuilder.Nil


public fun nil(): MyExpression<Nil> = object : MyExpression<Nil> {
    override fun toElement(): JsonElement = JsonNull.INSTANCE
    override fun computeValue(): Nil = Nil
    override fun materialize(): MyExpression<Nil> = this
    override fun isNil(): Boolean = true
}

@Suppress("UNCHECKED_CAST")
public fun nilOr(inExpression:MyExpression<*>, expression:MyExpression<*>) : MyExpression<Any> = MyComputedExpression {
    if (inExpression.isNil()) {
        nil() as MyExpression<Any>
    } else {
        expression as MyExpression<Any>
    }
}

public fun get(expression: MyExpression<Any>, path: String): MyExpression<*> = MyComputedExpression {
    val obj = expression.asMyObject()
    @Suppress("UNCHECKED_CAST")
    (get(obj, path) as MyExpression<Any>)
}

private fun get(obj: MyObject, path:String) : MyExpression<*> {
    val index = path.indexOf(".")
    if (index != -1) {
        val propertyName = path.substring(0, index)
        val rest = path.substring(index + 1)
        val value = obj.properties[propertyName]
        if ( value is MyObject) {
            return get(value, rest)
        }
        throw IllegalArgumentException("The property at path $path is no object.")
    }
    val result = obj.properties[path]
    if (result != null) {
        return result
    }
    throw IllegalArgumentException("The property at path $path does not exist.")

}