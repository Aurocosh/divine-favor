package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.stack_properties.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.PropertyGenerator
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

open class ItemMemoryDrop(name: String, texturePath: String, orderIndex: Int = 0) : ModItem(name, texturePath, orderIndex) {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler(name)
    val properties: IPropertyAccessor = propertyHandler

    init {
        propertyHandler.registerProperty(uuid)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {

        return EnumActionResult.PASS
    }

    override fun onItemRightClick(worldIn: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        return actionResult(false, stack)
    }

    companion object {
        val uuid = PropertyGenerator.stack.makeUUIDProperty("uuid", UUID.randomUUID(), showInTooltip = false, showInGui = false)
    }
}

