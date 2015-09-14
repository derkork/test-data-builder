package de.janthomae.databuilder

import de.janthomae.databuilder.expressions.lastMonth
import de.janthomae.databuilder.expressions.*
import org.junit.Test


class JsonBuilderTest {

    @Test
    fun testSimpleBuilder(): Unit {


        val devices = (20 * obj {
            // build the known devices
            prop("deviceId", uuid())
            prop("vendor", oneOf("Miele", "EnBW", "Acme", "Qivicon"))
            prop("type", oneOf("Dimmable Socket", "BlindsUnit", "Switch"))
            prop("model", oneOf("Model A", "Model B", "Model C"))
            prop("version", oneOf("1.0", "2.0"))
            prop("firmwareVersion", oneOf("1.0", "1.4"))
            prop("controllerId", uuid())
            prop("addedAt", isoDate(lastMonth(5)))
        }).materialize()


        val result = obj {
            prop("data",
                    1 * obj {
                        prop("collectedAt", isoDate(lastMonth(5)))
                        prop("submissionId", uuid())
                        prop("homebaseIdentifier", string("Homebase ") + name())

                        prop("entries",
                                choose(devices, int(1, 3))
                        )
                    }
            )
        }



        println(result.toJson(true))
    }
}

