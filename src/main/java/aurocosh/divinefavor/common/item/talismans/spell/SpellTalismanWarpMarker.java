package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.ItemWarpMarker;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SpellTalismanWarpMarker extends ItemSpellTalisman {
    private final ModItem item;

    public SpellTalismanWarpMarker(String name, int startingSpellUses, ModItem item) {
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
