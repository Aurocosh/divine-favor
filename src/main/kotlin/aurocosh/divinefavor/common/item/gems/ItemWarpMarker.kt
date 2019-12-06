package aurocosh.divinefavor.common.item.gems

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ICastable
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gems.base.IUsableGemItem
import aurocosh.divinefavor.common.item.gems.properties.GemMaskProperties
import aurocosh.divinefavor.common.item.gems.properties.GemPositionProperties
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilItemStack.actionResult
import aurocosh.divinefavor.common.util.UtilItemStack.actionResultPass
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemWarpMarker(name: String, private val canTeleportToDimensions: Boolean, override val consumeOnUse: Boolean, override val favorCost: Int, override val spirit: ModSpirit, maxStackSize: Int) : ModItem(name, name, ConstGemTabOrder.OTHER_GEMS), IPropertyContainer, IUsableGemItem, ISelectedStackProvider, ICastable {
    override val properties: IPropertyAccessor = propertyHandler

    init {
        setMaxStackSize(maxStackSize)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val context = CastContextGenerator.rightClick(world, player, hand, stack, stack)
        val result = cast(context)
        return actionResult(result, stack)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        val context = CastContextGenerator.useCast(player, world, pos, hand, facing, stack, stack)
        val result = cast(context)
        return actionResultPass(result)
    }

    override fun cast(context: CastContext): Boolean {
        if (context.world.isRemote)
            return false

        val stack = context.stack
        if (stack.isEmpty)
            return false
        if (stack.item !is ItemWarpMarker)
            return false
        if (!stack.isPropertySet(position))
            return false

        val player = context.player
        val dimension = stack.get(dimension)
        if (!canTeleportToDimensions && dimension != player.dimension)
            return false
        if (!consumeFavor(player))
            return false

        val destination = stack.get(position)
        UtilEntity.teleport(player, dimension, destination)
        if (consumeOnUse)
            stack.shrink(1)
        return true
    }

    private fun consumeFavor(player: EntityPlayer): Boolean {
        if (favorCost == 0)
            return true
        val spiritData = player.divinePlayerData.spiritData
        return spiritData.consumeFavor(spirit.id, favorCost)
    }

    override fun getSelectedStack(stack: ItemStack): ItemStack = stack

    companion object {
        val propertyHandler = StackPropertyHandler()
        val position = propertyHandler.registerProperty(GemPositionProperties.position)
        val dimension = propertyHandler.registerProperty(GemPositionProperties.dimension)
        val maskItemId = propertyHandler.registerProperty(GemMaskProperties.maskItemId)
        val maskItemMeta = propertyHandler.registerProperty(GemMaskProperties.maskItemMeta)
    }
}