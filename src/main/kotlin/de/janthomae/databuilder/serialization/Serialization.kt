package de.janthomae.databuilder.serialization

import com.google.gson.GsonBuilder
import de.janthomae.databuilder.MyArray
import de.janthomae.databuilder.MyObject
import de.janthomae.databuilder.Nil
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.QuoteMode
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.util.*

/**
 * Creates a JSON element from a [MyObject].
 */
public fun MyObject.toJson(pretty: Boolean = true): String {
    val builder = GsonBuilder()
    if (pretty) {
        builder.setPrettyPrinting()
    }
    return builder.create().toJson(toElement())
}

/**
 * Creates CSV from a [MyArray].
 */
public fun MyArray<Any>.toCsv(columnSeparator: Char = ',', rowSeparator: String = "\n", quotationMark: Char = '"', addHeader: Boolean = true): String {
    val materialized = materialize().computeValue();
    val numberOfLines = materialized.size()

    if (numberOfLines == 0) {
        throw IllegalStateException("Array needs at least one entry to be serialized to CSV.")
    }

    val firstElement = materialized.first() as MyObject

    val result = StringBuilder()
    var format = CSVFormat.DEFAULT.withDelimiter(columnSeparator).withRecordSeparator(rowSeparator).withQuote(quotationMark).withQuoteMode(QuoteMode.NON_NUMERIC)
    if (addHeader) {
        val strings = firstElement.properties.keySet().toTypedArray()
        format = format.withHeader(*strings)
    }

    val printer = CSVPrinter(result, format)


    val values = arrayOfNulls<Any>(firstElement.properties.size())
    for (expression in materialized) {
        var index = 0
        for (prop in (expression as MyObject).properties) {
            values[index++] = prop.value.computeValue()
        }
        printer.printRecord(*values)
    }

    printer.close()
    return result.toString()
}

/**
 * Creates SQL insert statements from a [MyArray].
 */
public fun MyArray<Any>.toSqlInsert(table: String, format: SqlSerializationFormat, columnSelector: ColumnSelector = ColumnSelector.all()): String {
    return generateSql(this, columnSelector) {
        "INSERT INTO $table(${format.columnQuote + it.keySet().join(format.columnQuote + "," + format.columnQuote) + format.columnQuote}) VALUES (${it.values().joinToString()});\n"
    }
}

/**
 * Creates SQL update statements from a [MyArray]
 */
public fun MyArray<Any>.toSqlUpdate(table: String, format: SqlSerializationFormat, idColumn: String, columnSelector: ColumnSelector = ColumnSelector.all()): String {
    return generateSql(this, columnSelector.include(idColumn)) {
        "UPDATE $table SET ${it.entrySet().filter { it.key != idColumn }.
                joinToString(transform = { format.columnQuote + it.key + format.columnQuote + "=" + it.value }) } where " +
                "${format.columnQuote+idColumn+format.columnQuote} = ${it.get(idColumn)};\n"
    }
}

private fun generateSql(data: MyArray<Any>, columnSelector: ColumnSelector,
                        printer: (LinkedHashMap<String, Any>) -> String): String {
    val materialized = data.materialize().computeValue();
    val builder = StringBuilder()

    for (element in materialized) {
        val properties = linkedMapOf<String, Any>()
        val obj = element.asMyObject()
        for (property in obj.properties) {
            if (columnSelector.accept(property.getKey())) {
                val value = property.getValue().computeValue()

                properties.put(property.getKey(),
                        when (value) {
                            is Number -> value.toString()
                            is Nil -> "NULL"
                            else -> "'${value.toString().replace("\\", "\\\\").replace("'", "\\'")}'"
                        }
                )
            }
        }
        builder.append(printer(properties))
    }
    return builder.toString()
}

/**
 * Writes the given string to a file.
 */
public fun writeToFile(value: String, outputPath: String, append: Boolean = false, charset: Charset = Charsets.UTF_8) {
    val file = File(outputPath)
    val parent = file.parentFile
    if (parent != null && !parent.exists()) {
        parent.mkdirs()
    }

    FileOutputStream(file, append).use {
        it.write(value.toByteArray(charset))
    }
}

