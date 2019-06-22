package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.attackEntityNoTimer
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource

class BladeTalismanObliteration(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        return context.target !is EntityPlayer
    }

    override fun performActionServer(context: TalismanContext) {
        val target = context.target ?: return
        val extraLootingData = target.divineLivingData.extraLootingData
        extraLootingData.isLootDenied = true
        extraLootingData.isExperienceDenied = ConfigBlade.obliteration.denyExperience
        target.attackEntityNoTimer(DamageSource.MAGIC, ConfigBlade.obliteration.damage)
    }
}
