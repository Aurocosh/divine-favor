package aurocosh.divinefavor.common.item.spell_talismans.base

import aurocosh.divinefavor.common.config.common.ConfigGeneral
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

private data class StackData(val raycastBlock: Boolean, val raycastTarget: Boolean, val valid: Boolean)
private data class BlockCastData(val pos: BlockPos, val posVec: Vec3d, val facing: EnumFacing, val valid: Boolean)

data class CommonContextValues(val player: EntityPlayer, val stack: ItemStack, val world: World)

class TalismanContext(val player: EntityPlayer, target: EntityLivingBase?, val world: World, pos: BlockPos, posVec: Vec3d, val hand: EnumHand, facing: EnumFacing, val castType: CastType, val stack: ItemStack) {
    val target: EntityLivingBase?
    val pos: BlockPos
    val posVec: Vec3d
    val facing: EnumFacing
    val valid: Boolean // TODO split validity

    init {
        val (raycastBlock, raycastTarget, stackValid) = getStackData(stack)
        val castData = getBlockCastData(player, pos, posVec, facing, raycastBlock)
        this.pos = castData.pos
        this.posVec = castData.posVec
        this.facing = castData.facing
        this.valid = stackValid && castData.valid
        if (raycastTarget)
            this.target = UtilEntity.getEntityPlayerLookingAt(player, EntityLivingBase::class.java, ENTITY_SEARCH_DISTANCE, true)
        else
            this.target = target
    }

    private fun getStackData(stack: ItemStack): StackData {
        val item = stack.item
        if (stack.isEmpty || item !is ItemTalisman)
            return StackData(false, false, false)
        return StackData(item.raycastBlock(), item.raycastTarget(), true)
    }

    fun getCommon() = CommonContextValues(player, stack, world)

    companion object {
        fun blade(stack: ItemStack, target: EntityLivingBase?, player: EntityPlayer): TalismanContext {
            return TalismanContext(player, target, player.world, player.position, player.positionVector, EnumHand.MAIN_HAND, EnumFacing.UP, CastType.BladeCast, stack)
        }

        fun useCast(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, stack: ItemStack): TalismanContext {
            return TalismanContext(player, null, world, pos, pos.toVec3d(), hand, facing, CastType.UseCast, stack)
        }

        fun generic(player: EntityPlayer, hand: EnumHand, stack: ItemStack): TalismanContext {
            return TalismanContext(player, null, player.world, player.position, player.positionVector, hand, EnumFacing.UP, CastType.None, stack)
        }

        fun rightClick(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack): TalismanContext {
            return TalismanContext(player, null, world, player.position, player.positionVector, hand, EnumFacing.UP, CastType.RightCast, stack)
        }

        fun arrowShot(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack): TalismanContext {
            val position = player.position
            return TalismanContext(player, null, world, position, position.toVec3d(), hand, EnumFacing.UP, CastType.RightCast, stack)
        }

        private val ENTITY_SEARCH_DISTANCE = 30.0

        private fun getBlockCastData(player: EntityPlayer, pos: BlockPos, posVec: Vec3d, facing: EnumFacing, raycastBlock: Boolean): BlockCastData {
            if (!raycastBlock)
                return BlockCastData(pos, posVec, facing, true)
            val traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance.toDouble())
            if (traceResult != null)
                return BlockCastData(traceResult.blockPos, traceResult.hitVec, traceResult.sideHit, true)
            return BlockCastData(pos, posVec, facing, false)
        }
    }
}
