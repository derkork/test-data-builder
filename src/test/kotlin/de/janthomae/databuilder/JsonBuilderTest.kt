package de.janthomae.databuilder

import de.janthomae.databuilder.expressions.lastMonth
import de.janthomae.databuilder.expressions.*
import org.junit.Test


class JsonBuilderTest {

    @Test
    fun testSimpleBuilder(): Unit {

        val row = obj {
            prop("Index", counter(1))
            prop("Serial Number", uuid())
            prop("Item ID", uuid())
            prop("TR-069 Password", uuid())
            // part numbers look like this: QHB-3102-001-EU
            prop("Part Number", string("QHB") + "-" + padLeft(4, int(0, 9999)) + "-" + padLeft(3, int(0,999)))
        }


        write(row.toCsv(100), "/Users/j.thomae/Desktop/homebase20.csv")
    }
}

