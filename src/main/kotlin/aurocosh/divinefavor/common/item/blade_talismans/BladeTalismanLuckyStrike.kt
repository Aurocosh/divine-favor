package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer

class BladeTalismanLuckyStrike(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: CastContext): Boolean {
        return context.target !is EntityPlayer
    }

    override fun performActionServer(context: CastContext) {
        val target = context.target ?: return
        val lootingData = target.divineLivingData.extraLootingData

        val looting = lootingData.extraLooting + ConfigBlade.luckyStrike.extraLootingPerHit
        lootingData.extraLooting = Math.min(looting, ConfigBlade.luckyStrike.extraLootingCap)
    }
}
