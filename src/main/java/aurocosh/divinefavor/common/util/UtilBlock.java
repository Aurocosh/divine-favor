package aurocosh.divinefavor.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
    private static boolean canReplaceBlock(EntityPlayer player, World world, BlockPos pos) {
        if (world.isRemote)
            return false;
        if (!world.isBlockLoaded(pos))
            return false;
        if(!world.isBlockModifiable(player, pos))
            return false;
        return world.getBlockState(pos).getBlock() != Blocks.BEDROCK;
    }

    public static boolean canBreakBlock(EntityPlayer player, World world, BlockPos pos, boolean isToolRequired) {
        if (!canReplaceBlock(player, world, pos))
            return false;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.isAir(state, world, pos) || block instanceof BlockLiquid || block instanceof IFluidBlock || !(block.getPlayerRelativeBlockHardness(state, player, world, pos) > 0))
            return false;
        if (isToolRequired && !ForgeHooks.canHarvestBlock(block, player, world, pos))
            return false;
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    private static void destroyBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        Block block = state.getBlock();
        if (player.capabilities.isCreativeMode)
            world.setBlockToAir(pos);
        if (block.removedByPlayer(state, world, pos, player, true))
            block.onPlayerDestroy(world, pos, state);
    }

    public static boolean removeBlock(EntityPlayer player, World world, ItemStack tool, BlockPos pos, boolean dropItem, boolean isToolRequired, boolean particles) {
        if (!canBreakBlock(player, world, pos, isToolRequired))
            return false;
        IBlockState state = world.getBlockState(pos);
        destroyBlock(player, world, pos, state);
        if (!player.capabilities.isCreativeMode && dropItem)
            state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos), tool);
        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state));
        return true;
    }

    public static boolean removeBlockAndReplant(EntityPlayer player, World world, ItemStack tool, BlockPos pos, boolean isToolRequired, boolean particles) {
        if (!canBreakBlock(player, world, pos, isToolRequired))
            return false;
        IBlockState state = world.getBlockState(pos);
        destroyBlock(player, world, pos, state);
        harvestBlockAndReplant(world, player, pos, state, 0, tool);
        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state));
        return true;
    }

    private static void harvestBlockAndReplant(World world, EntityPlayer player, BlockPos pos, IBlockState state, int fortune, ItemStack stack) {
        Block block = state.getBlock();
        player.addStat(StatList.getBlockStats(block));
        player.addExhaustion(0.005F);

        // first, try getting a seed from the drops, if we don't have one we don't replant
        float chance = 1.0f;
        NonNullList<ItemStack> drops = NonNullList.create();
        state.getBlock().getDrops(drops, world, pos, state, fortune);
        chance = ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, chance, false, player);

        IPlantable seed = null;
        for (ItemStack drop : drops) {
            if (drop != null && drop.getItem() instanceof IPlantable) {
                seed = (IPlantable) drop.getItem();
                drop.shrink(1);
                if (drop.isEmpty())
                    drops.remove(drop);
                break;
            }
        }

        if (seed != null) {
            // make sure the plant is allowed here. should already be, mainly just covers the case of seeds from grass
            IBlockState down = world.getBlockState(pos.down());
            if (down.getBlock().canSustainPlant(down, world, pos.down(), EnumFacing.UP, seed)) {
                // success! place the plant and drop the rest of the items
                IBlockState crop = seed.getPlant(world, pos);

                // only place the block/damage the tool if its a different state
                if (crop != state)
                    world.setBlockState(pos, seed.getPlant(world, pos));

                // drop the remainder of the items
                for (ItemStack drop : drops)
                    if (world.rand.nextFloat() <= chance)
                        Block.spawnAsEntity(world, pos, drop);
            }
        }
    }

    public static boolean smeltBlock(EntityPlayer player, World world, BlockPos pos) {
        if (world.isRemote)
            return false;
        if (!world.isBlockModifiable(player, pos))
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
        return replaceBlock(player, world, pos, block.getDefaultState());
    }

    public static boolean replaceBlock(EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        if (!canReplaceBlock(player, world, pos))
            return false;
        world.setBlockState(pos, newState);
        return true;
    }

    public static boolean replaceBlock(EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumHand hand) {
        Item item = stack.getItem();
        if (!(item instanceof ItemBlock))
            return false;
        if (!canReplaceBlock(player, world, pos))
            return false;

        ItemBlock itemBlock = (ItemBlock) item;
        Block blockToPlace = Block.getBlockFromItem(itemBlock);
        IBlockState stateForPlacement = blockToPlace.getStateForPlacement(world, pos, EnumFacing.UP, 0, 0, 0, stack.getItemDamage(), player, hand);
        itemBlock.placeBlockAt(stack, player, world, pos, EnumFacing.UP, 0, 0, 0, stateForPlacement);
        return true;
    }

    public static boolean isAirOrReplaceable(World world, BlockPos pos) {
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    public static boolean ignite(EntityPlayer player, World world, BlockPos pos) {
        if (isAirOrReplaceable(world, pos))
            return replaceBlock(player, world, pos, Blocks.FIRE.getDefaultState());
        return false;
    }

    public static boolean ignite(EntityPlayer player, World world, BlockPos pos, EnumFacing facing) {
        if (ignite(player, world, pos))
            return true;

        Vec3i shift = facing.getDirectionVec();
        if (ignite(player, world, pos.add(shift)))
            return true;
        return false;
    }

    public static boolean isWater(Block block) {
        return block == Blocks.WATER || block == Blocks.FLOWING_WATER;
    }

    public static boolean isLava(Block block) {
        return block == Blocks.LAVA || block == Blocks.FLOWING_LAVA;
    }

    public static boolean isLiquid(Block block) {
        return block instanceof BlockStaticLiquid || block instanceof BlockDynamicLiquid;
    }
}