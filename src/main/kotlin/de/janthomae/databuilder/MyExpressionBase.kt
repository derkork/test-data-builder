package de.janthomae.databuilder


abstract class MyExpressionBase<T> : MyExpression<T> {
    var value:T = null

    constructor(value: T) {
        this.value = value
    }

    var function: (() -> T)? = null

    constructor(function: () -> T) {
        this.function = function
    }

    public override fun computeValue() : T {
        val f = function
        if (f!= null) {
            return f()
        }
        else {
            return value
        }
    }

}
