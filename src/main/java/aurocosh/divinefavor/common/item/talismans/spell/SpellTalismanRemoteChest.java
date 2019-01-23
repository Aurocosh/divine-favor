package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.ItemWarpMarker;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellTalismanRemoteChest extends ItemSpellTalisman {
    private final ModItem item;

    public SpellTalismanRemoteChest() {
        super("remote_chest", 10, true, true);
        this.item = ModItems.storage_gem;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        if(!isValid(context.world, context.pos))
            return;

        EntityPlayer player = context.player;
        ItemStack stack = new ItemStack(item);
        NBTTagCompound nbt = UtilNbt.getTag(stack);
        UtilNbt.setBlockPos(nbt, ItemWarpMarker.TAG_POSITION, context.pos);
        nbt.setInteger(ItemWarpMarker.TAG_DIMENSION, player.dimension);
        player.inventory.addItemStackToInventory(stack);
    }

    private boolean isValid(World world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == Blocks.CHEST;
    }

    @Override
    protected boolean isConsumeCharge(TalismanContext context) {
        return isValid(context.world, context.pos);
    }
}
