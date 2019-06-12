package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLiving
import net.minecraft.inventory.EntityEquipmentSlot

class BladeTalismanCorrosion(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: TalismanContext) {
        val target = context.target as? EntityLiving ?: return

        for (armorSlot in armorSlots) {
            val stack = target.getItemStackFromSlot(armorSlot)
            if (!stack.isEmpty && UtilRandom.rollDiceFloat(ConfigBlade.corrosion.corrosionChance)) {
                val damage = ConfigBlade.corrosion.corrosionPower.random()
                stack.damageItem(damage, context.player)
            }
        }
    }

    companion object {
        val armorSlots: Array<EntityEquipmentSlot> = arrayOf(EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET)
    }
}
