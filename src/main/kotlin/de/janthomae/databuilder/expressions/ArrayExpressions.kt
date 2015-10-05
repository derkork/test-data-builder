package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

operator public fun MyInt.times<T>(obj: MyExpression<T>): MyExpression<Array<MyExpression<T>>> =
        MyComputedExpression {
            this.computeValue() * obj
        }

operator public fun Int.times<T>(obj: MyExpression<T>): MyArray<T> = toLong() * obj

operator public fun Long.times<T>(obj: MyExpression<T>): MyArray<T> =
        MyArray<T> {
            val lst = arrayListOf<MyExpression<T>>()
            for (i in 1..this) {
                lst.add(obj)
            }
            lst.toTypedArray()
        }

public fun currentIndex(): MyInt = MyInt { MyArray.Index.peek() }

public fun oneOf<T>(array: MyExpression<Array<MyExpression<T>>>): MyExpression<T> =
        MyComputedExpression {
            val computed = array.computeValue()
            computed[ThreadLocalRandom.current().nextInt(computed.size())]
        }

public fun oneOf(vararg input: MyExpression<*>) : MyExpression<*> = MyComputedExpression<Any?> {
    @Suppress("UNCHECKED_CAST")
    (input[ThreadLocalRandom.current().nextInt(0, input.size())] as MyExpression<Any?>)
}


public fun augment(array: MyExpression<Array<MyExpression<Any>>>, init: MyObject.() -> Unit): MyArray<Any> = MyArray<Any> {
    val result = arrayListOf<MyExpression<Any>>()
    val arrayValue = array.computeValue()
    for (item in arrayValue) {
        result.add(item)
        if (item is MyObject) {
            item.init()
        } else {
            throw IllegalArgumentException("Augment only works for objects!")
        }
    }
    result.toTypedArray()
}

public fun choose<T>(array: MyExpression<Array<MyExpression<T>>>, amount: Int): MyArray<T> = choose(array, MyInt(amount))

public fun choose<T>(array: MyExpression<Array<MyExpression<T>>>, amount: MyInt): MyArray<T> =
        MyArray<T> {
            val realAmount = amount.computeValue()
            if (realAmount == 0L) {
                emptyArray()
            } else {
                val values = array.computeValue()
                val list = arrayListOf(*values)
                Collections.shuffle(list)
                list.subList(0, realAmount.toInt()).toTypedArray()
            }
        }

public fun at<R, T : MyExpression<R>>(array: MyExpression<Array<T>>, index: Int): MyExpression<R> = at(array, MyInt(index))
public fun at<R, T : MyExpression<R>>(array: MyExpression<Array<T>>, index: MyInt): MyExpression<R> =
        MyComputedExpression<R> {
            val computedArray = array.computeValue()
            computedArray[index.computeValue().toInt() % computedArray.size()]

        }

public fun cross(array: MyExpression<Array<MyExpression<Any>>>, array2: MyExpression<Array<MyExpression<Any>>>, init: MyCombinedObject.() -> Unit):
        MyArray<Any> = MyArray<Any> {
    val result = arrayListOf<MyExpression<Any>>()
    val arrayValue = array.computeValue()
    for (first in arrayValue) {
        val arrayValue2 = array2.computeValue()
        for (second in arrayValue2) {
            val obj = MyCombinedObject(first, second)
            obj.init()
            result.add(obj)
        }
    }
    result.toTypedArray()
}

public class MyCombinedObject(val first: MyExpression<*>, val second: MyExpression<*>) : MyObject() {
    public fun firstObject(): MyObject {
        if (first is MyObject) {
            return first
        }
        throw IllegalStateException("first object is not an object")
    }

    public fun secondObject(): MyObject {
        if (second is MyObject) {
            return second
        }
        throw IllegalStateException("second object is not an object")
    }

}
