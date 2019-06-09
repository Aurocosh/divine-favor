package aurocosh.divinefavor.common.custom_data.living.data.extra_looting

class ExtraLootingData {
    var extraLooting: Int = 0

    fun resetCureTimer() {
        extraLooting = 0
    }

    fun addLooting(looting: Int) {
        extraLooting += looting
    }
}
