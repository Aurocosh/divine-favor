package aurocosh.divinefavor.common.item.talismans.spell.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

open class ItemSpellTalisman// Talisman functions
(name: String, texturePath: String, spirit: ModSpirit, favorCost: Int, private val options: EnumSet<SpellOptions>) : ItemTalisman("talisman_$name", "spell_talismans/$texturePath$name", spirit, favorCost) {

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_SPELL_TALISMANS
    }

    private fun getTexturePath(path: Array<String>): String {
        return path.joinToString("/")
    }

    // Talisman functions
    constructor(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : this(name, "", spirit, favorCost, options)

    fun cast(context: TalismanContext): Boolean {
        if (!validate(context))
            return false
        if (!claimCost(context))
            return false
        if (context.world.isRemote)
            performActionClient(context)
        else
            performActionServer(context)
        return true
    }

    protected fun claimCost(context: TalismanContext): Boolean {
        if (favorCost == 0)
            return true
        if (!isConsumeCharge(context))
            return true
        val spiritData = context.player.divinePlayerData.spiritData
        if (!spiritData.consumeFavor(spirit.id, favorCost))
            return false
        if (context.world.isRemote)
            return true

        MessageSyncFavor(spirit, spiritData).sendTo(context.player)
        return true
    }
    // Talisman functions


    override fun onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = playerIn.getHeldItem(hand)
        if (stack.item is ItemSpellTalisman) {
            val casted = castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ)
            return if (casted) EnumActionResult.SUCCESS else EnumActionResult.PASS
        }
        return EnumActionResult.PASS
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(hand)
        if (stack.item !is ItemSpellTalisman)
            return ActionResult(EnumActionResult.PASS, stack)
        val success = castRightClick(worldIn, playerIn, hand)
        return ActionResult(if (success) EnumActionResult.SUCCESS else EnumActionResult.PASS, stack)
    }

    fun castItemUse(playerIn: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (!options.contains(SpellOptions.ItemUseCast))
            return false
        val context = TalismanContext(playerIn, world, pos, Vec3d(hitX.toDouble(), hitY.toDouble(), hitZ.toDouble()), hand, facing, null, CastType.UseCast, options)
        cast(context)
        return true
    }

    fun castRightClick(world: World, player: EntityPlayer, hand: EnumHand): Boolean {
        if (!options.contains(SpellOptions.RightClickCast))
            return false

        val pos: BlockPos
        val posVec: Vec3d
        val facing: EnumFacing
        var target: EntityLivingBase? = null
        if (options.contains(SpellOptions.OnRightCastRayTraceBlock)) {
            val traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance.toDouble())
                    ?: return false
            pos = traceResult.blockPos
            posVec = traceResult.hitVec
            facing = traceResult.sideHit
        } else {
            pos = player.position
            posVec = Vec3d(pos)
            facing = EnumFacing.UP
        }
        if (options.contains(SpellOptions.OnRightCastFindTargetEntity))
            target = UtilEntity.getEntityPlayerLookingAt(player, EntityLivingBase::class.java, ENTITY_SEARCH_DISTANCE, true)

        val context = TalismanContext(player, world, pos, posVec, hand, facing, target, CastType.RightCast, options)
        return cast(context)
    }

    protected open fun validate(context: TalismanContext): Boolean {
        return true
    }

    protected open fun isConsumeCharge(context: TalismanContext): Boolean {
        return true
    }

    protected open fun performActionServer(context: TalismanContext) {}

    protected open fun performActionClient(context: TalismanContext) {}

    companion object {
        private val ENTITY_SEARCH_DISTANCE = 30.0
    }
}