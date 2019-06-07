package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.registry.ModRegistries
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilDayTime
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider

class SpiritInfoProcessor : IComponentProcessor {
    private var text: String = ""
    private var spirit: ModSpirit? = null

    override fun setup(variables: IVariableProvider<String>) {
        val name = variables.get("spirit")
        val spiritId = ResourceLocation(name)
        spirit = ModRegistries.spirits.getValue(spiritId)
        if (spirit == null)
            DivineFavor.logger.error("Spirit not found:$name")

        text = variables.get("text")
    }

    override fun process(key: String): String? {
        val spirit = spirit
        if (key == "text") {
            var result = text
            if (spirit != null) {
                val activityPeriod = spirit.activityPeriod
                val start = activityPeriod.start / UtilDayTime.TICKS_IN_HOUR
                val stop = activityPeriod.stop / UtilDayTime.TICKS_IN_HOUR
                result += String.format("$(br)This spirit is active from %d o'clock to %d o'clock.", start, stop)
            }
            return result
        }
        return null
    }
}