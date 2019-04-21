package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.block.BlockEnderPumpkin;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemBlockEnderPumpkin extends ModItemBlock {
    public ItemBlockEnderPumpkin(BlockEnderPumpkin block, int orderIndex) {
        super(block, orderIndex);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType == EntityEquipmentSlot.HEAD;
    }
}
