import de.janthomae.databuilder.data.gtin
import de.janthomae.databuilder.data.uuidShort
import de.janthomae.databuilder.expressions.*
import de.janthomae.databuilder.obj
import de.janthomae.databuilder.serialization.*

fun main(args: Array<String>) {
    val date = sqlDateTime(lastMonth(12), lastMonth(6))

    val customerCount = 50000

    val customers = (customerCount * obj {
        prop("id", uuidShort())
        prop("type", "customer")
        prop("external_id", uuidShort())
        prop("user_id", string("QUDE") + gtin(int(210000000001, 210999999999)))
        prop("language", "de")
        prop("market", oneOf(
                "01f642ffd69e4226b642ffd69e122655",
                "039a5eeeeb8e46cd9a5eeeeb8e46cd74",
                "2635b3f6beb6489eb5b3f6beb6989e3b",
                "312c36dfc36a41eeac36dfc36a41ee4b",
                "6cfd53dd351348edbd53dd351358ed3e",
                "6db457a2917d4559b457a2917dd559a5",
                "727abec49de04204babec49de0a20493",
                "895adb77aec646bc9adb77aec6a6bcfd",
                "99b5f716dbd94324b5f716dbd9c32452",
                "b00cac736ff64d968cac736ff6dd9696",
                "bb8dbb9ec91144098dbb9ec911c4095e",
                "dfecfcb669754c55acfcb669755c554e"
        ))
        prop("created_at", date)
        prop("last_modified_at", date)
        prop("show_email_address", 0)
        prop("state", "Active")
        prop("no_ccm_email_notification", 0)
    }).materialize()

    val homebases = (customerCount * obj {
        prop("id", uuidShort())
        prop("created_at", date)
        prop("last_modified_at", date)
        prop("homebase_serial", uuidShort())
        prop("registered_on", date)
        prop("customer", get(at(customers, counter(0)), "id"))
    }).materialize()

    val subscriptions = (2 * customerCount * obj {
        prop("id", uuidShort())
        prop("created_at", date)
        prop("last_modified_at", date)
        prop("auto_install", int(0))
        prop("subscription_id", string("SUDE") + gtin(int(380000000001, 380999999999)))
        prop("application_license", uuidShort())
        prop("customer", get(at(customers, counter(0)), "id"))
        prop("software", oneOf("9ee2182efd30437ca2182efd30137c82", "e8dd3c5bbcd240a79d3c5bbcd230a71e"))
    }).materialize()

    var applicationLicenses = (2 * customerCount * obj {
        prop("id", get(at(subscriptions, counter(0)), "application_license"))
        prop("created_at", date)
        prop("last_modified_at", date)
        prop("channel", oneOf("PartnerSales", "QiviconInternal", "PartnerFUP", "QiviconFUP", "PartnerSupport"))
        prop("has_been_deprovisioned", 0)
        prop("start_date", oneOf(nil(), date))
        prop("terminated", int(0, 1))
        prop("customer", get(at(subscriptions, counter(0)), "customer"))
        prop("software", get(at(subscriptions, counter(0)), "software"))
        prop("subscription", get(at(subscriptions, counter(0)), "id"))
        prop("voucher", nil())
        prop("validity_in_days", int(100, 365))
        prop("application_license_id", string("ALDE") + gtin(int(340000000001, 340999999999)))
        prop("blocked", int(0, 1))
        prop("sales_channel", oneOf(
                "11bd03fe2e6143febd03fe2e6163fe05",
                "5261987573334cbda198757333fcbd24",
                "94310e743c704eccb10e743c701eccef",
                "ff8081814eb51560014eb51623980001"))
    }).materialize()

    applicationLicenses = augment(applicationLicenses, {
        prop("end_date", nilOr(get(this, "start_date"), sqlDateTime(now(), nextMonth(6))))
    })

    writeToFile(customers.toSqlInsert("qc20_user", SqlSerializationFormat.mysql()), "/Users/j.thomae/Downloads/Inbox/data.sql")
    writeToFile(homebases.toSqlInsert("qc20_homebase", SqlSerializationFormat.mysql()), "/Users/j.thomae/Downloads/Inbox/data.sql", true)
    writeToFile(applicationLicenses.toSqlInsert("qc20_application_license", SqlSerializationFormat.mysql(), ColumnSelector.allBut("subscription")), "/Users/j.thomae/Downloads/Inbox/data.sql", true)
    writeToFile(subscriptions.toSqlInsert("qc20_subscription", SqlSerializationFormat.mysql()), "/Users/j.thomae/Downloads/Inbox/data.sql", true)
    writeToFile(applicationLicenses.toSqlUpdate("qc20_application_license", SqlSerializationFormat.mysql(), "id", de.janthomae.databuilder.serialization.ColumnSelector.only("subscription")), "/Users/j.thomae/Downloads/Inbox/data.sql", true)


}