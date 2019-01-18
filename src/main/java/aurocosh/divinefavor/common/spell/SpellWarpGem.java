package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.item.ItemWarpPebble;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SpellWarpGem extends ModSpell {
    private final ModItem item;

    public SpellWarpGem(ModItem item) {
        this.item = item;
    }

    @Override
    protected void performActionServer(SpellContext context) {
        EntityPlayer player = context.player;
        ItemStack stack = new ItemStack(item);
        NBTTagCompound nbt = UtilNbt.getEistingOrNewNBT(stack);
        UtilNbt.setBlockPos(nbt, ItemWarpPebble.TAG_POSITION, player.getPosition());
        nbt.setInteger(ItemWarpPebble.TAG_DIMENSION, player.dimension);
        player.inventory.addItemStackToInventory(stack);
    }
}
