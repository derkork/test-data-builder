package de.janthomae.databuilder

import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset


public fun obj(init: MyObject.() -> Unit): MyObject {
    val result = MyObject()
    result.init()
    return result
}


public fun write(value: String, outputPath: String, charset: Charset = Charsets.UTF_8) {
    val file = File(outputPath)
    val parent = file.parent
    if (parent != null && !parent.exists()) {
        parent.mkdirs()
    }

    val stream = FileOutputStream(file)
    stream.write(value.toByteArray(charset))
    stream.close();
}
