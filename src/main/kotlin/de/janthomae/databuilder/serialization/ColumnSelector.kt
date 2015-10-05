package de.janthomae.databuilder.serialization

import kotlin.text.Regex

public class ColumnSelector(val include: Array<out String>, val exclude: Array<out String>) {

    public fun accept(column: String): Boolean {
        var matches = false
        for (i in include) {
            if (Regex(i).matches(column)) {
                matches = true
                break
            }
        }

        if (!matches) return false

        for (e in exclude) {
            if (Regex(e).matches(column)) {
                return false
            }
        }

        return true
    }

    public fun include(vararg selection: String) = ColumnSelector(arrayOf(*selection) + include, exclude)

    companion object {
        public fun only(vararg patterns: String) = ColumnSelector(patterns, emptyArray())
        public fun allBut(vararg patterns: String) = ColumnSelector(arrayOf(".*"), patterns)
        public fun all() = ColumnSelector(arrayOf(".*"), emptyArray())
    }

}