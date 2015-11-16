package de.janthomae.databuilder.data

import de.janthomae.databuilder.MyInt


/**
 * Converts the given long value to a GTIN/EAN13 with calculated digit. If it is not long enough it will be left padded
 * with zeroes.
 */
public fun gtin(value: MyInt): MyInt = MyInt {
    val ean = value.computeValue().toString().padStart(12, '0')
    if (ean.length > 12) throw IllegalArgumentException("EAN input $ean is too long.")
    val sum = ean.toCharArray().mapIndexed { i, c -> (if (i % 2 == 0) 1 else 3) * c.toString().toInt() }.sum()
    (ean + ((10 - (sum % 10)) % 10)).toLong()
}

public fun gtin(value: Long): MyInt = gtin(MyInt(value))
public fun gtin(value: Int): MyInt = gtin(MyInt(value))