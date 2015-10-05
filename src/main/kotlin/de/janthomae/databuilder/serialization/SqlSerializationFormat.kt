package de.janthomae.databuilder.serialization

interface SqlSerializationFormat {
    val columnQuote : String

    companion object {
        /**
         * A serialization format for mysql.
         */
        public fun mysql(): SqlSerializationFormat = object : SqlSerializationFormat {
            override val columnQuote: String
                get() = "`"
        }
    }
}

