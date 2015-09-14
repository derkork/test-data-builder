package de.janthomae.databuilder

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import java.util.*

public class MyArray<T> : MyExpressionBase<Array<MyExpression<T>>> {

    constructor(value:Array<MyExpression<T>>) : super(value)
    constructor(function: () -> Array<MyExpression<T>>) : super(function)

    override fun toElement(): JsonElement {
        val result = JsonArray()
        val theValue = computeValue()
        Index.push(0)
        for (i in theValue) {
            result.add(i.toElement())
            Index.push(Index.pop()+1)
        }
        Index.pop()
        return result
    }

    override fun materialize() : MyArray<T> {
        val theValue = computeValue()
        val list = arrayListOf<MyExpression<T>>()
        for (i in theValue) {
            list.add(i.materialize())
        }
        return MyArray(list.toTypedArray())
    }


    companion  object Index : Stack<Int>() {
    }

}


