package de.janthomae.databuilder

import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

public fun obj(init: MyObject.() -> Unit): MyObject {
    val result = MyObject()
    result.init()
    return result
}



