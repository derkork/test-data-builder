package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyInt
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger


public fun int(to: Int): MyInt = int(0, to)
public fun int(from: Int, to: Int): MyInt = MyInt { ThreadLocalRandom.current().nextInt(from, to+1) }
public fun counter(start:Int = 1) : MyInt = Counter(start)


private class Counter(start:Int) : MyInt(0) {
    val counter =  AtomicInteger(start)
    override fun computeValue(): Int = counter.getAndIncrement()
}