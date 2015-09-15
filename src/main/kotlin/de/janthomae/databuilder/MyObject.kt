package de.janthomae.databuilder

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.QuoteMode

public open class MyObject : MyExpression<Any> {


    var properties = linkedMapOf<String, MyExpression<*>>()

    override fun computeValue(): Any {
        throw UnsupportedOperationException("ComputeValue is undefined for objects.")
    }

    override fun toElement(): JsonElement {
        val result = JsonObject()
        for (i in properties) {
            result.add(i.key, i.value.toElement())
        }
        return result
    }

    public fun <T : MyExpression<*>> prop(name: String, expression: T): T {
        properties[name] = expression
        return expression
    }

    public fun prop(name: String, init: MyObject.() -> Unit): MyObject {
        val obj = prop(name, MyObject())
        obj.init()
        return obj
    }

    public fun prop(name: String, value: String): MyString {
        return prop(name, MyString(value))
    }

    public fun prop(name: String, value: Int): MyInt {
        return prop(name, MyInt(value))
    }

    public fun get(path: String): MyExpression<*> {
        val index = path.indexOf(".")
        if (index != -1) {
            val propertyName = path.substring(0, index)
            val rest = path.substring(index + 1)
            val value = properties[propertyName]
            if ( value is MyObject) {
                return value.get(rest)
            }
            throw IllegalArgumentException("The property at path " + path + " is no object.")
        }
        val result = properties[path]
        if (result != null) {
            return result;
        }
        throw IllegalArgumentException("The property at path " + path + " does not exist.")
    }

    public fun toJson(pretty: Boolean = true): String {
        val builder = GsonBuilder()
        if (pretty) {
            builder.setPrettyPrinting()
        }
        return builder.create().toJson(toElement())
    }

    public fun toCsv(numberOfLines: Int, columnSeparator: Char = ',', rowSeparator: String = "\n", quotationMark: Char = '"', addHeader: Boolean = true): String {
        val result = StringBuilder()
        var format = CSVFormat.DEFAULT.withDelimiter(columnSeparator).withRecordSeparator(rowSeparator).withQuote(quotationMark).withQuoteMode(QuoteMode.NON_NUMERIC)
        if (addHeader) {
            val strings = properties.keySet().toTypedArray()
            format = format.withHeader(*strings)
        }
        val printer = CSVPrinter(result, format)

        val values = arrayOfNulls<Any>(properties.size())
        for ( i in 1..numberOfLines ) {
            var index = 0
            for ( prop in properties) {
                values[index++] = prop.value.computeValue()
            }
            printer.printRecord(*values)
        }

        printer.close()
        return result.toString()
    }


    override fun materialize(): MyExpression<Any> {
        val copy = linkedMapOf<String, MyExpression<*>>()
        for ( i in properties) {
            copy[i.key] = i.value.materialize()
        }
        val result = MyObject()
        result.properties = copy
        return result
    }


}