package aurocosh.divinefavor.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.IFluidBlock;

public class UtilBlock {
    public static boolean removeBlockWithDrops(EntityPlayer player, World world, ItemStack tool, BlockPos pos, boolean isToolRequired, boolean particles) {
        if (world.isRemote)
            return false;
        if (!world.isBlockLoaded(pos))
            return false;
        if (!world.isBlockModifiable(player, pos))
            return false;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.isAir(state, world, pos) || block instanceof BlockLiquid || block instanceof IFluidBlock || !(block.getPlayerRelativeBlockHardness(state, player, world, pos) > 0))
            return false;
        if (isToolRequired && !ForgeHooks.canHarvestBlock(block, player, world, pos))
            return false;

        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return false;

        if (!player.capabilities.isCreativeMode) {
            TileEntity tile = world.getTileEntity(pos);
            if (block.removedByPlayer(state, world, pos, player, true)) {
                block.onPlayerDestroy(world, pos, state);
                block.harvestBlock(world, player, pos, state, tile, tool);
            }
        }
        else
            world.setBlockToAir(pos);

        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state));
        return true;
    }

    public static boolean smeltBlock(EntityPlayer player, World world, BlockPos pos) {
        if(world.isRemote)
            return false;
        if(!world.isBlockModifiable(player, pos))
            return false;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        int meta = block.getMetaFromState(state);
        ItemStack stack = new ItemStack(block, 1, meta);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
        if (result.isEmpty())
            return false;

        Block blockResult = Block.getBlockFromItem(result.getItem());
        if (blockResult == Blocks.AIR)
            return false;

        world.setBlockState(pos, blockResult.getStateFromMeta(result.getMetadata()));
        state = world.getBlockState(pos);
        world.playEvent(2001, pos, Block.getStateId(state));
        return true;
    }

    public static boolean isAirOrReplaceable(World world, BlockPos pos){
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }
}