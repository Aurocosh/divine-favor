package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.common.block.BlockEnderPumpkin
import aurocosh.divinefavor.common.item.base.ModItemBlock
import net.minecraft.entity.Entity
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

class ItemBlockEnderPumpkin(block: BlockEnderPumpkin, orderIndex: Int) : ModItemBlock(block, orderIndex) {

    override fun isValidArmor(stack: ItemStack, armorType: EntityEquipmentSlot?, entity: Entity?): Boolean {
        return armorType == EntityEquipmentSlot.HEAD
    }
}
