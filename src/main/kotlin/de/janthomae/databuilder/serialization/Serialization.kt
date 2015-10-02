package de.janthomae.databuilder.serialization

import com.google.gson.GsonBuilder
import de.janthomae.databuilder.MyArray
import de.janthomae.databuilder.MyObject
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.QuoteMode
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset


public fun MyObject.toJson(pretty: Boolean = true): String {
    val builder = GsonBuilder()
    if (pretty) {
        builder.setPrettyPrinting()
    }
    return builder.create().toJson(toElement())
}

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

public fun MyArray<Any>.toSql(table: String, vararg columns: String): String {
    val materialized = materialize().computeValue();
    val numberOfLines = materialized.size()

    if (numberOfLines == 0) {
        throw IllegalStateException("Array needs at least one entry to be serialized to SQL.")
    }

    val firstElement = materialized.first() as MyObject

    val finalColumns = if (columns.size() == 0) firstElement.properties.keySet().toTypedArray() else columns

    val result = StringBuilder()

    for (expression in materialized) {
        val obj = expression as MyObject
        val values = finalColumns.map { it -> obj.properties.get(it)?.computeValue() }.map { it -> if (it is Number) it.toString() else "'${it.toString().replace("'", "\\'")}'" }
        result.append("INSERT INTO $table(${finalColumns.joinToString()}) VALUES (${values.joinToString()});\n")
    }
    return result.toString()
}

public fun writeToFile(value: String, outputPath: String, charset: Charset = Charsets.UTF_8) {
    val file = File(outputPath)
    val parent = file.parentFile
    if (parent != null && !parent.exists()) {
        parent.mkdirs()
    }

    val stream = FileOutputStream(file)
    stream.write(value.toByteArray(charset))
    stream.close();
}
