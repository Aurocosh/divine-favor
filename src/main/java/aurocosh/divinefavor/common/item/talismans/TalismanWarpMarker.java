package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.ItemWarpMarker;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TalismanWarpMarker extends ItemTalisman {
    private final ModItem item;

    public TalismanWarpMarker(String name, int startingSpellUses, ModItem item) {
        super(name, startingSpellUses, true, true);
        this.item = item;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        ItemStack stack = new ItemStack(item);
        NBTTagCompound nbt = UtilNbt.getTag(stack);
        UtilNbt.setBlockPos(nbt, ItemWarpMarker.TAG_POSITION, player.getPosition());
        nbt.setInteger(ItemWarpMarker.TAG_DIMENSION, player.dimension);
        player.inventory.addItemStackToInventory(stack);
    }
}
