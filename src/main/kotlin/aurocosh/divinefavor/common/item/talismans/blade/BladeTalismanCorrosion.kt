package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

class BladeTalismanCorrosion(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        if (target !is EntityLiving)
            return

        for (armorSlot in armorSlots) {
            val stack = target.getItemStackFromSlot(armorSlot)
            if (!stack.isEmpty && UtilRandom.rollDiceFloat(ConfigBlade.corrosion.corrosionChance)) {
                val damage = ConfigBlade.corrosion.corrosionPower.random()
                stack.damageItem(damage, player)
            }
        }
    }

    companion object {
        val armorSlots: Array<EntityEquipmentSlot> = arrayOf(EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET)
    }
}
