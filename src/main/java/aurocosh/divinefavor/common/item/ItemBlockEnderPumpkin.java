package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.block.BlockEnderPumpkin;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEnderPumpkin extends ItemBlock {
    public ItemBlockEnderPumpkin(BlockEnderPumpkin block) {
        super(block);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType == EntityEquipmentSlot.HEAD;
    }
}
