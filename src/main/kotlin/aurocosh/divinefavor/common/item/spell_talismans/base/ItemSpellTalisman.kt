package aurocosh.divinefavor.common.item.spell_talismans.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContextGenerator
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import aurocosh.divinefavor.common.util.UtilItem.actionResultPass
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

open class ItemSpellTalisman// Talisman functions
(name: String, texturePath: String, spirit: ModSpirit, favorCost: Int, protected val options: EnumSet<SpellOptions>) : ItemTalisman("spell_talisman_$name", "spell_talismans/$texturePath$name", spirit, favorCost) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_SPELL_TALISMANS
    }

    constructor(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : this(name, "", spirit, favorCost, options)

    private fun getTexturePath(path: Array<String>): String {
        return path.joinToString("/")
    }

    override fun raycastBlock(stack: ItemStack, castType: CastType): Boolean {
        return options.contains(SpellOptions.OnRightCastRayTraceBlock)
    }

    override fun raycastTarget(stack: ItemStack, castType: CastType): Boolean {
        return options.contains(SpellOptions.OnRightCastFindTargetEntity)
    }

    override fun preValidate(context: TalismanContext): Boolean {
        if (context.castType == CastType.UseCast && !options.contains(SpellOptions.ItemUseCast))
            return false
        if (context.castType == CastType.RightCast && !options.contains(SpellOptions.RightClickCast))
            return false
        return super.preValidate(context)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        val context = TalismanContextGenerator.useCast(player, world, pos, hand, facing, stack)
        val success = cast(context)
        return actionResultPass(success)
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val context = TalismanContextGenerator.rightClick(world, player, hand, stack)
        val success = cast(context)
        return actionResult(success, stack)
    }
}