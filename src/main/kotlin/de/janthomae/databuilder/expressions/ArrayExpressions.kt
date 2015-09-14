package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyComputedExpression
import de.janthomae.databuilder.MyArray
import de.janthomae.databuilder.MyExpression
import de.janthomae.databuilder.MyInt
import java.util.Collections
import java.util.concurrent.ThreadLocalRandom

public fun MyInt.times<T>(obj: MyExpression<T>): MyExpression<Array<MyExpression<T>>> =
        MyComputedExpression {
            this.computeValue() * obj
        }


public fun Int.times<T>(obj: MyExpression<T>): MyArray<T> =
        MyArray {
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

public fun choose<T>(array: MyExpression<Array<MyExpression<T>>>, amount: MyInt): MyArray<T> =
        MyArray {
            val realAmount = amount.computeValue()
            if (realAmount == 0) {
                emptyArray()
            }
            else {
                val values = array.computeValue()
                val list = arrayListOf(*values)
                Collections.shuffle(list)
                list.subList(0, realAmount).toTypedArray()
            }
        }

public fun at<T>(array: MyExpression<Array<MyExpression<T>>>, index: MyInt): MyExpression<T> =
        MyComputedExpression { array.computeValue()[index.computeValue()] }
