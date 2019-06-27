package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.item.spell_talismans.copy.MetaItem
import com.google.common.collect.ImmutableSet
import net.minecraft.block.*
import net.minecraft.block.BlockPortal.AXIS
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

object UtilBlockState {
    private val safeProperties = setOf(BlockSlab.HALF, BlockStairs.HALF, BlockLog.LOG_AXIS, AXIS, BlockDirectional.FACING,
            BlockStairs.FACING, BlockTrapDoor.HALF, BlockTorch.FACING, BlockStairs.SHAPE, BlockLever.FACING, BlockLever.POWERED, BlockRedstoneRepeater.DELAY,
            BlockStoneSlab.VARIANT, BlockWoodSlab.VARIANT, BlockDoubleWoodSlab.VARIANT, BlockDoubleStoneSlab.VARIANT)

    private val safePropertiesCopyPaste = ImmutableSet.builder<IProperty<*>>().addAll(safeProperties)
            .addAll(setOf(BlockDoubleWoodSlab.VARIANT, BlockRail.SHAPE, BlockRailPowered.SHAPE)).build()

    fun getSpecificStates(originalState: IBlockState, world: World, player: EntityPlayer, pos: BlockPos, isCopyPaste: Boolean): IBlockState {
        val item = originalState.block.getPickBlock(originalState, null, world, pos, player)
        val meta = item.metadata

        var placeState = try {
            originalState.block.getStateForPlacement(world, pos, EnumFacing.UP, 0f, 0f, 0f, meta, player, EnumHand.MAIN_HAND)
        } catch (exception: Exception) {
            originalState.block.defaultState
        }

        val safeProperties = if (isCopyPaste) safePropertiesCopyPaste else safeProperties
        for (prop: IProperty<*> in placeState.propertyKeys) {
            if (safeProperties.contains(prop)) {
                val iProperty = prop as IProperty<Comparable<Any>>
                placeState = placeState.withProperty<Comparable<Any>, Comparable<Any>>(iProperty, originalState.getValue<Comparable<Any>>(iProperty))
            }
        }
        return placeState
    }

    fun blockStateToUniqueItem(state: IBlockState, player: EntityPlayer, pos: BlockPos): MetaItem {
        var stack = try {
            state.block.getPickBlock(state, null, player.world, pos, player)
        } catch (e: Exception) {
            UtilBlock.getSilkTouchDrop(state)
        }

        if (stack.isEmpty)
            stack = UtilBlock.getSilkTouchDrop(state)
        return if (!stack.isEmpty)
            MetaItem(stack.item, stack.metadata)
        else
            MetaItem(Items.AIR, 0)
    }
}