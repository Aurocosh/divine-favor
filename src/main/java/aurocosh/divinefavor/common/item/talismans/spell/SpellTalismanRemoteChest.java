package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.ItemWarpMarker;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class SpellTalismanRemoteChest extends ItemSpellTalisman {
    private final ModItem item;

    public SpellTalismanRemoteChest(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options, ModItem item) {
        super(name, favor, favorCost, options);
        this.item = item;
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
