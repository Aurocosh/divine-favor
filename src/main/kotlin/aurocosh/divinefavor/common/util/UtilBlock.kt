package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.block.doppel.TileEtherealGoo
import net.minecraft.block.Block
import net.minecraft.block.BlockLiquid
import net.minecraft.block.material.EnumPushReaction
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.stats.StatBase
import net.minecraft.stats.StatList
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.BlockSnapshot
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fluids.IFluidBlock

object UtilBlock {
    fun canReplaceBlock(player: EntityPlayer, world: World, pos: BlockPos): Boolean {
        if (pos.y < 0)
            return false
        if (!player.isAllowEdit)
            return false
        if (world.isRemote)
            return false
        if (!world.isBlockLoaded(pos))
            return false
        return if (!world.isBlockModifiable(player, pos)) false else world.getBlockState(pos).block !== Blocks.BEDROCK
    }

    fun canBreakBlock(player: EntityPlayer, world: World, pos: BlockPos, isToolRequired: Boolean): Boolean {
        if (!canReplaceBlock(player, world, pos))
            return false

        val state = world.getBlockState(pos)
        val block = state.block
        if (block.isAir(state, world, pos) || block is BlockLiquid || block is IFluidBlock)
            return false
        if (isToolRequired && !ForgeHooks.canHarvestBlock(block, player, world, pos))
            return false
        val event = BlockEvent.BreakEvent(world, pos, state, player)
        MinecraftForge.EVENT_BUS.post(event)
        return !event.isCanceled
    }

    private fun destroyBlock(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState) {
        val block = state.block
        if (player.capabilities.isCreativeMode)
            world.setBlockToAir(pos)
        if (block.removedByPlayer(state, world, pos, player, true))
            block.onPlayerDestroy(world, pos, state)
    }

    fun removeBlock(player: EntityPlayer, world: World, tool: ItemStack, pos: BlockPos, dropItem: Boolean, isToolRequired: Boolean, particles: Boolean): Boolean {
        if (!canBreakBlock(player, world, pos, isToolRequired))
            return false
        val state = world.getBlockState(pos)
        destroyBlock(player, world, pos, state)
        if (!player.capabilities.isCreativeMode && dropItem)
            state.block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), tool)
        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state))
        return true
    }

    fun removeBlockAndReplant(player: EntityPlayer, world: World, pos: BlockPos, isToolRequired: Boolean, particles: Boolean): Boolean {
        if (!canBreakBlock(player, world, pos, isToolRequired))
            return false
        val state = world.getBlockState(pos)
        destroyBlock(player, world, pos, state)
        harvestBlockAndReplant(world, player, pos, state, 0)
        if (particles)
            world.playEvent(2001, pos, Block.getStateId(state))
        return true
    }

    private fun harvestBlockAndReplant(world: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, fortune: Int) {
        val block = state.block
        player.addStat(StatList.getBlockStats(block) as StatBase)
        player.addExhaustion(0.005f)

        // first, try getting a seed from the drops, if we don't have one we don't replant
        var chance = 1.0f
        val drops = NonNullList.create<ItemStack>()
        state.block.getDrops(drops, world, pos, state, fortune)
        chance = ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, chance, false, player)

        var seed: IPlantable? = null
        for (drop in drops) {
            if (drop != null && drop.item is IPlantable) {
                seed = drop.item as IPlantable
                drop.shrink(1)
                if (drop.isEmpty)
                    drops.remove(drop)
                break
            }
        }

        if (seed != null) {
            // make sure the plant is allowed here. should already be, mainly just covers the case of seeds from grass
            val down = world.getBlockState(pos.down())
            if (down.block.canSustainPlant(down, world, pos.down(), EnumFacing.UP, seed)) {
                // success! place the plant and drop the rest of the items
                val crop = seed.getPlant(world, pos)

                // only place the block/damage the tool if its a different state
                if (crop !== state)
                    world.setBlockState(pos, seed.getPlant(world, pos))

                // drop the remainder of the items
                for (drop in drops)
                    if (world.rand.nextFloat() <= chance)
                        Block.spawnAsEntity(world, pos, drop)
            }
        }
    }

    fun smeltBlock(player: EntityPlayer, world: World, pos: BlockPos): Boolean {
        if (world.isRemote)
            return false
        if (!world.isBlockModifiable(player, pos))
            return false

        var state = world.getBlockState(pos)
        val block = state.block
        val meta = block.getMetaFromState(state)
        val stack = ItemStack(block, 1, meta)
        val result = FurnaceRecipes.instance().getSmeltingResult(stack)
        if (result.isEmpty)
            return false

        val blockResult = Block.getBlockFromItem(result.item)
        if (blockResult === Blocks.AIR)
            return false

        world.setBlockState(pos, blockResult.getStateFromMeta(result.metadata))
        state = world.getBlockState(pos)
        world.playEvent(2001, pos, Block.getStateId(state))
        return true
    }

    fun replaceBlock(player: EntityPlayer, world: World, pos: BlockPos, block: Block): Boolean {
        return replaceBlock(player, world, pos, block.defaultState)
    }

    fun replaceBlock(player: EntityPlayer, world: World, pos: BlockPos, newState: IBlockState): Boolean {
        if (!canReplaceBlock(player, world, pos))
            return false
        val blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pos)
        if (ForgeEventFactory.onPlayerBlockPlace(player, blockSnapshot, EnumFacing.UP, EnumHand.MAIN_HAND).isCanceled)
            return false
        world.setBlockState(pos, newState)
        return true
    }

    fun replaceBlockWithGoo(player: EntityPlayer, world: World, pos: BlockPos, newState: IBlockState): Boolean {
        if (!canReplaceBlock(player, world, pos))
            return false
        val blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pos)
        if (ForgeEventFactory.onPlayerBlockPlace(player, blockSnapshot, EnumFacing.UP, EnumHand.MAIN_HAND).isCanceled)
            return false

        val gooState = ModBlocks.ethereal_goo.defaultState
        world.setBlockState(pos, gooState)

        val tileEntity = world.getTileEntity(pos) as? TileEtherealGoo ?: return false
        tileEntity.blockState = newState
        tileEntity.actualBlockState = newState
        return true
    }

    fun replaceBlock(player: EntityPlayer, world: World, pos: BlockPos, stack: ItemStack, hand: EnumHand): Boolean {
        val item = stack.item as? ItemBlock ?: return false
        if (!canReplaceBlock(player, world, pos))
            return false
        val blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pos)
        if (ForgeEventFactory.onPlayerBlockPlace(player, blockSnapshot, EnumFacing.UP, hand).isCanceled)
            return false
        val blockToPlace = Block.getBlockFromItem(item)
        val stateForPlacement = blockToPlace.getStateForPlacement(world, pos, EnumFacing.UP, 0f, 0f, 0f, stack.itemDamage, player, hand)
        item.placeBlockAt(stack, player, world, pos, EnumFacing.UP, 0f, 0f, 0f, stateForPlacement)
        return true
    }

    fun moveBlock(player: EntityPlayer, world: World, pos: BlockPos, facing: EnumFacing): Boolean {
        val state = world.getBlockState(pos)
        val block = state.block
        if (world.getTileEntity(pos) != null)
            return false
        if (state.pushReaction != EnumPushReaction.NORMAL)
            return false
        if (!block.canSilkHarvest(world, pos, state, player))
            return false
        if (state.getPlayerRelativeBlockHardness(player, world, pos) <= 0)
            return false
        if (!canBreakBlock(player, world, pos, false))
            return false

        val shiftVec = facing.directionVec
        val shiftPos = pos.add(shiftVec)

        if (!canReplaceBlock(player, world, shiftPos))
            return false
        if (!isAirOrReplaceable(world, shiftPos))
            return false

        world.setBlockState(shiftPos, state, 1 or 2)
        world.setBlockToAir(pos)
        return true
    }

    fun isAirOrReplaceable(world: World, pos: BlockPos): Boolean {
        return world.isAirBlock(pos) || world.getBlockState(pos).block.isReplaceable(world, pos)
    }

    fun ignite(player: EntityPlayer, world: World, pos: BlockPos): Boolean {
        return if (isAirOrReplaceable(world, pos)) replaceBlock(player, world, pos, Blocks.FIRE.defaultState) else false
    }

    fun ignite(player: EntityPlayer, world: World, pos: BlockPos, facing: EnumFacing): Boolean {
        if (ignite(player, world, pos))
            return true

        val shift = facing.directionVec
        return ignite(player, world, pos.add(shift))
    }

    fun getSilkDropIfPresent(world: World, state: IBlockState, player: EntityPlayer): ItemStack {
        if (state.block.canSilkHarvest(world, BlockPos.ORIGIN, state, player))
            return getSilkTouchDrop(state)
        return state.block.getPickBlock(state, null, world, BlockPos.ORIGIN, player)
    }

    fun getSilkTouchDrop(state: IBlockState): ItemStack {
        val item = Item.getItemFromBlock(state.block)
        val meta = if (item.hasSubtypes) state.block.damageDropped(state) else 0
        return ItemStack(item, 1, meta)
    }

}