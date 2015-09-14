package de.janthomae.databuilder

import de.janthomae.databuilder.expressions.*
import org.junit.Test

class CsvBuilderTest {

    @Test
    fun testSimpleBuilder(): Unit {




        val result =  obj {
            prop("index", counter(1))
            prop("itemId", isoDate(lastMonth()))
            prop("endorsementKey", uuid())
            prop("encryptedEndorsementKey", uuid())
            prop("lorem", lorem(int(3,5)))
            prop("int", int(5, 20))
        }



        println(result.toCsv(20, columnSeparator = ';'))
    }
}
