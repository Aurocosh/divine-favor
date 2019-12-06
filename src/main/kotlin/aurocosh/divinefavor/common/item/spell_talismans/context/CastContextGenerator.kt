package aurocosh.divinefavor.common.item.spell_talismans.context

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.toVec3d
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

object CastContextGenerator {
    private data class StackData(val raycastBlock: Boolean, val raycastTarget: Boolean, val valid: Boolean)
    private data class BlockCastData(val pos: BlockPos, val posVec: Vec3d, val facing: EnumFacing, val valid: Boolean)

    fun generate(player: EntityPlayer, target: EntityLivingBase?, world: World, pos: BlockPos, posVec: Vec3d, hand: EnumHand, facing: EnumFacing, castType: CastType, talismanStack: ItemStack, containerStack: ItemStack): CastContext {
        val (raycastBlock, raycastTarget, stackValid) = getStackData(talismanStack, castType)
        val (posFinal, posVecFinal, facingFinal, raycastValid) = getBlockCastData(player, pos, posVec, facing, raycastBlock)
        val targetFinal = if (raycastTarget) UtilEntity.getEntityPlayerLookingAt(player, EntityLivingBase::class.java, ENTITY_SEARCH_DISTANCE, true) else target

        return CastContext(player, targetFinal, world, posFinal, posVecFinal, hand, facingFinal, castType, talismanStack, raycastValid, stackValid, containerStack)
    }

    private fun getStackData(stack: ItemStack, castType: CastType): StackData {
        val item = stack.item
        if (stack.isEmpty || item !is ItemTalisman)
            return StackData(false, false, false)
        return StackData(item.raycastBlock(stack, castType), item.raycastTarget(stack, castType), true)
    }

    private fun getBlockCastData(player: EntityPlayer, pos: BlockPos, posVec: Vec3d, facing: EnumFacing, raycastBlock: Boolean): BlockCastData {
        if (!raycastBlock)
            return BlockCastData(pos, posVec, facing, false)
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance.toDouble())
        if (traceResult != null)
            return BlockCastData(traceResult.blockPos, traceResult.hitVec, traceResult.sideHit, true)
        return BlockCastData(pos, posVec, facing, false)
    }


    fun blade(stack: ItemStack, target: EntityLivingBase?, player: EntityPlayer, containerStack: ItemStack): CastContext {
        return generate(player, target, player.world, player.position, player.positionVector, EnumHand.MAIN_HAND, EnumFacing.UP, CastType.BladeCast, stack, containerStack)
    }

    fun pick(stack: ItemStack, player: EntityPlayer, pos: BlockPos, containerStack: ItemStack): CastContext {
        val facing = EnumFacing.getDirectionFromEntityLiving(pos, player)
        return generate(player, null, player.world, pos, pos.toVec3d(), EnumHand.MAIN_HAND, facing, CastType.PickCast, stack, containerStack)
    }

    fun useCast(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, stack: ItemStack, containerStack: ItemStack): CastContext {
        return generate(player, null, world, pos, pos.toVec3d(), hand, facing, CastType.UseCast, stack, containerStack)
    }

    fun generic(player: EntityPlayer, hand: EnumHand, stack: ItemStack, containerStack: ItemStack): CastContext {
        return generate(player, null, player.world, player.position, player.positionVector, hand, EnumFacing.UP, CastType.None, stack, containerStack)
    }

    fun player(player: EntityPlayer): CastContext {
        return generate(player, null, player.world, player.position, player.positionVector, EnumHand.MAIN_HAND, EnumFacing.UP, CastType.None, ItemStack.EMPTY, ItemStack.EMPTY)
    }

    fun rightClick(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack, containerStack: ItemStack): CastContext {
        return generate(player, null, world, player.position, player.positionVector, hand, EnumFacing.UP, CastType.RightCast, stack, containerStack)
    }

    fun arrowShot(world: World, player: EntityPlayer, stack: ItemStack, containerStack: ItemStack): CastContext {
        val position = player.position
        return generate(player, null, world, position, position.toVec3d(), EnumHand.MAIN_HAND, EnumFacing.UP, CastType.BowCast, stack, containerStack)
    }

    private const val ENTITY_SEARCH_DISTANCE = 30.0
}
