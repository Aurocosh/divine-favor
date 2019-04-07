package aurocosh.divinefavor.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.IFluidBlock;

public class UtilBlock {
    public static boolean canBreakBlock(EntityPlayer player, World world, BlockPos pos, boolean isToolRequired) {
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
        if(isToolRequired && !ForgeHooks.canHarvestBlock(block, player, world, pos))
            return false;
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    private static void removeBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        Block block = state.getBlock();
        if (player.capabilities.isCreativeMode)
            world.setBlockToAir(pos);
        if (block.removedByPlayer(state, world, pos, player, true))
            block.onPlayerDestroy(world, pos, state);
    }

    public static boolean removeBlockWithDrops(EntityPlayer player, World world, ItemStack tool, BlockPos pos, boolean isToolRequired, boolean particles) {
        if(!canBreakBlock(player, world, pos, isToolRequired))
            return false;
        IBlockState state = world.getBlockState(pos);
        removeBlock(player, world, pos, state);
        if (!player.capabilities.isCreativeMode)
            state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos), tool);
        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state));
        return true;
    }

    public static boolean removeBlockAndReplant(EntityPlayer player, World world, ItemStack tool, BlockPos pos, boolean isToolRequired, boolean particles) {
        if(!canBreakBlock(player, world, pos, isToolRequired))
            return false;
        IBlockState state = world.getBlockState(pos);
        removeBlock(player, world, pos, state);
        harvestBlockAndReplant(world, player, pos, state, 0, tool);
        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state));
        return true;
    }

    private static void harvestBlockAndReplant(World world, EntityPlayer player, BlockPos pos, IBlockState state, int fortune, ItemStack stack)
    {
        Block block = state.getBlock();
        player.addStat(StatList.getBlockStats(block));
        player.addExhaustion(0.005F);

        // first, try getting a seed from the drops, if we don't have one we don't replant
        float chance = 1.0f;
        NonNullList<ItemStack> drops = NonNullList.create();
        state.getBlock().getDrops(drops, world, pos, state, fortune);
        chance = ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, chance, false, player);

        IPlantable seed = null;
        for(ItemStack drop : drops) {
            if(drop != null && drop.getItem() instanceof IPlantable) {
                seed = (IPlantable) drop.getItem();
                drop.shrink(1);
                if(drop.isEmpty())
                    drops.remove(drop);
                break;
            }
        }

        // if we have a valid seed, try to plant the crop
        boolean replanted = false;
        if(seed != null) {
            // make sure the plant is allowed here. should already be, mainly just covers the case of seeds from grass
            IBlockState down = world.getBlockState(pos.down());
            if(down.getBlock().canSustainPlant(down, world, pos.down(), EnumFacing.UP, seed)) {
                // success! place the plant and drop the rest of the items
                IBlockState crop = seed.getPlant(world, pos);

                // only place the block/damage the tool if its a different state
                if(crop != state)
                    world.setBlockState(pos, seed.getPlant(world, pos));

                // drop the remainder of the items
                for(ItemStack drop : drops)
                    if (world.rand.nextFloat() <= chance)
                        Block.spawnAsEntity(world, pos, drop);
                replanted = true;
            }
        }
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

    public static boolean replaceBlock(EntityPlayer player, World world, BlockPos pos, Block block) {
        if(world.isRemote)
            return false;
        if(!world.isBlockModifiable(player, pos))
            return false;
        world.setBlockState(pos, block.getDefaultState());
        return true;
    }

    public static boolean isAirOrReplaceable(World world, BlockPos pos){
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    public static boolean ignite(World world, BlockPos pos){
        if (!isAirOrReplaceable(world, pos))
            return false;
        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        return true;
    }

    public static boolean ignite(World world, BlockPos pos, EnumFacing facing){
        if(ignite(world, pos))
            return true;

        Vec3i shift = facing.getDirectionVec();
        if(ignite(world, pos.add(shift)))
            return true;
        return false;
    }
}