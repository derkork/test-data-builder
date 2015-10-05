package de.janthomae.databuilder

public fun obj(init: MyObject.() -> Unit): MyObject {
    val result = MyObject()
    result.init()
    return result
}



