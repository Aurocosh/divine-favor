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

object TalismanContextGenerator {
    private data class StackData(val raycastBlock: Boolean, val raycastTarget: Boolean, val valid: Boolean)
    private data class BlockCastData(val pos: BlockPos, val posVec: Vec3d, val facing: EnumFacing, val valid: Boolean)

    fun generate(player: EntityPlayer, target: EntityLivingBase?, world: World, pos: BlockPos, posVec: Vec3d, hand: EnumHand, facing: EnumFacing, castType: CastType, stack: ItemStack): TalismanContext {
        val (raycastBlock, raycastTarget, stackValid) = getStackData(stack)
        val (posFinal, posVecFinal, facingFinal, raycastValid) = getBlockCastData(player, pos, posVec, facing, raycastBlock)
        val targetFinal = if (raycastTarget) UtilEntity.getEntityPlayerLookingAt(player, EntityLivingBase::class.java, ENTITY_SEARCH_DISTANCE, true) else target

        return TalismanContext(player, targetFinal, world, posFinal, posVecFinal, hand, facingFinal, castType, stack, raycastValid, stackValid)
    }

    private fun getStackData(stack: ItemStack): StackData {
        val item = stack.item
        if (stack.isEmpty || item !is ItemTalisman)
            return StackData(false, false, false)
        return StackData(item.raycastBlock(stack), item.raycastTarget(stack), true)
    }

    private fun getBlockCastData(player: EntityPlayer, pos: BlockPos, posVec: Vec3d, facing: EnumFacing, raycastBlock: Boolean): BlockCastData {
        if (!raycastBlock)
            return BlockCastData(pos, posVec, facing, false)
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance.toDouble())
        if (traceResult != null)
            return BlockCastData(traceResult.blockPos, traceResult.hitVec, traceResult.sideHit, true)
        return BlockCastData(pos, posVec, facing, false)
    }


    fun blade(stack: ItemStack, target: EntityLivingBase?, player: EntityPlayer): TalismanContext {
        return generate(player, target, player.world, player.position, player.positionVector, EnumHand.MAIN_HAND, EnumFacing.UP, CastType.BladeCast, stack)
    }

    fun pick(stack: ItemStack, player: EntityPlayer, pos: BlockPos): TalismanContext {
        val facing = EnumFacing.getDirectionFromEntityLiving(pos, player)
        return generate(player, null, player.world, pos, pos.toVec3d(), EnumHand.MAIN_HAND, facing, CastType.PickCast, stack)
    }

    fun useCast(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, stack: ItemStack): TalismanContext {
        return generate(player, null, world, pos, pos.toVec3d(), hand, facing, CastType.UseCast, stack)
    }

    fun generic(player: EntityPlayer, hand: EnumHand, stack: ItemStack): TalismanContext {
        return generate(player, null, player.world, player.position, player.positionVector, hand, EnumFacing.UP, CastType.None, stack)
    }

    fun rightClick(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack): TalismanContext {
        return generate(player, null, world, player.position, player.positionVector, hand, EnumFacing.UP, CastType.RightCast, stack)
    }

    fun arrowShot(world: World, player: EntityPlayer, stack: ItemStack): TalismanContext {
        val position = player.position
        return generate(player, null, world, position, position.toVec3d(), EnumHand.MAIN_HAND, EnumFacing.UP, CastType.BowCast, stack)
    }

    private const val ENTITY_SEARCH_DISTANCE = 30.0
}
