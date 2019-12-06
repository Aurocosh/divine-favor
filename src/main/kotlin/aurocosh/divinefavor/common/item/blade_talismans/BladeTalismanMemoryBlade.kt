package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.attackEntityNoTimer
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.DamageSource

class BladeTalismanMemoryBlade(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: CastContext) {
        val target = context.target ?: return
        val compound = context.stack.compound

        val targetUUID = target.uniqueID
        val stackUUID = compound.getUniqueId(TAG_VICTIM_UUID)
        if (targetUUID == stackUUID) {
            val damage = compound.getFloat(TAG_DAMAGE)
            target.attackEntityNoTimer(DamageSource.MAGIC, damage)

            val newDamage = Math.min(damage + ConfigBlade.memoryBlade.damagePerHit, ConfigBlade.memoryBlade.damageCap)
            compound.setFloat(TAG_DAMAGE, newDamage)
        } else {
            compound.setUniqueId(TAG_VICTIM_UUID, targetUUID)
            compound.setFloat(TAG_DAMAGE, ConfigBlade.memoryBlade.damagePerHit)
        }
    }

    companion object {
        const val TAG_VICTIM_UUID = "VictimUUID"
        const val TAG_DAMAGE = "Damage"
    }
}
